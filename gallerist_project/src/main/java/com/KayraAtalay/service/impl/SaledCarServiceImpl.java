package com.KayraAtalay.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.CurrencyRatesResponse;
import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoSaledCar;
import com.KayraAtalay.dto.DtoSaledCarIU;
import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.SaledCar;
import com.KayraAtalay.repository.CarRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.repository.GalleristRepository;
import com.KayraAtalay.repository.SaledCarRepository;
import com.KayraAtalay.service.ICurrencyRatesService;
import com.KayraAtalay.service.ISaledCarService;
import com.KayraAtalay.utils.DateUtils;

@Service
public class SaledCarServiceImpl implements ISaledCarService {

	@Autowired
	private SaledCarRepository saledCarRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private GalleristRepository galleristRepository;

	@Autowired
	private ICurrencyRatesService currencyRatesService;

	
	
	public BigDecimal convertCustomerAmountToUSD(Customer customer) {

		CurrencyRatesResponse currencyRatesResponse = currencyRatesService
				.getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
		BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

		BigDecimal customerUSDAmount = customer.getAccount().getAmount().divide(usd, 2, RoundingMode.HALF_UP);
		return customerUSDAmount;
	}

	private boolean checkCarStatus(Long carId) {

		Optional<Car> optCar = carRepository.findById(carId);
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, carId.toString()));
		}

		if (optCar.get().getCarStatusType().name().equalsIgnoreCase("SALABLE")) {
			return true;
		}

		return false;

	}

	public BigDecimal remainingCustomerAmount(Customer customer, Car car) {

		BigDecimal customerUSDAmount = convertCustomerAmountToUSD(customer);
		BigDecimal remainingCustomerUSDAmount = customerUSDAmount.subtract(car.getPrice());

		CurrencyRatesResponse currencyRatesResponse = currencyRatesService
				.getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
		BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

		return remainingCustomerUSDAmount.multiply(usd);

	}

	public boolean checkAmount(Customer customer, Car car) {

	    // Same currency type (TL to TL or USD to USD)
	    if (customer.getAccount().getCurrencyType().equals(car.getCurrencyType())) {
	        return customer.getAccount().getAmount().compareTo(car.getPrice()) >= 0;
	    } 
	    // Customer's account is TL, car price is USD
	    else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("TL") && car.getCurrencyType().name().equalsIgnoreCase("USD")) {
	        // Convert customer's TL amount to USD and check if it's enough
	        BigDecimal customerUSDAmount = convertCustomerAmountToUSD(customer);
	        return customerUSDAmount.compareTo(car.getPrice()) >= 0;
	    } 
	    // Customer's account is USD, car price is TL
	    else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("USD") && car.getCurrencyType().name().equalsIgnoreCase("TL")) {
	        // Convert car's TL price to USD and check against customer's USD amount
	        CurrencyRatesResponse currencyRatesResponse = currencyRatesService
	                .getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
	        BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
	        
	        BigDecimal carUSDPrice = car.getPrice().divide(usd, 2, RoundingMode.HALF_UP);
	        return customer.getAccount().getAmount().compareTo(carUSDPrice) >= 0;
	    }

	    // Default case for unsupported currency combinations
	    return false;
	}

	private SaledCar createSaledCar(DtoSaledCarIU dtoSaledCarIU) {
		SaledCar saledCar = new SaledCar();
		saledCar.setCreateTime(new Date());

		Optional<Customer> optCustomer = customerRepository.findById(dtoSaledCarIU.getCustomerId());
		if (optCustomer.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, ": " + dtoSaledCarIU.getCustomerId()));
		}
		Optional<Gallerist> optGallerist = galleristRepository.findById(dtoSaledCarIU.getGalleristId());
		if (optGallerist.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, ": " + dtoSaledCarIU.getGalleristId()));
		}

		Optional<Car> optCar = carRepository.findById(dtoSaledCarIU.getCarId());
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, ": " + dtoSaledCarIU.getCarId()));
		}

		
		saledCar.setCustomer(optCustomer.get());
		saledCar.setGallerist(optGallerist.get());
		saledCar.setCar(optCar.get());

		return saledCar;

	}

	@Override
	public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU) {

		if (!checkCarStatus(dtoSaledCarIU.getCarId())) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_IS_ALREADY_SOLD, ""));
		}
		
		BigDecimal newCustomerAmount;

		SaledCar savedSaledCar = saledCarRepository.save(createSaledCar(dtoSaledCarIU));
		Customer customer = savedSaledCar.getCustomer();
		Car car = savedSaledCar.getCar();
		
		if (!checkAmount(customer, car)) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH, ""));
		}
		
		
		
			//This transaction is for same currencyTypes(customer's account's currency type and car's price currency type
		 if (customer.getAccount().getCurrencyType().equals(car.getCurrencyType())) {
		        
		        newCustomerAmount = customer.getAccount().getAmount().subtract(car.getPrice());
		        
		        car.setCarStatusType(CarStatusType.SOLD);
		        carRepository.save(car);

		         customer.getAccount().setAmount(newCustomerAmount);
		        customerRepository.save(customer);
		        
		        return toDto(savedSaledCar);

		    }
		 
		 // customer == "TL", car price = "USD"
		 else if(customer.getAccount().getCurrencyType().name().equalsIgnoreCase("TL")
				 && car.getCurrencyType().name().equalsIgnoreCase("USD")) {
			 
			    car.setCarStatusType(CarStatusType.SOLD);

				carRepository.save(car);

				
				customer.getAccount().setAmount(remainingCustomerAmount(customer, car));
				customerRepository.save(customer);

				return toDto(savedSaledCar);
		 }
		 
		// customer == "USD", car price = "TL"
		 else if(customer.getAccount().getCurrencyType().name().equalsIgnoreCase("USD") && 
				 car.getCurrencyType().name().equalsIgnoreCase("TL")) {
			 		        
		        car.setCarStatusType(CarStatusType.SOLD);
		        carRepository.save(car);
		        
		        // Update customer's account with the remaining USD amount
		        customer.getAccount().setAmount(remainingCustomerAmountForUSD(customer, car));
		        customerRepository.save(customer);

		        return toDto(savedSaledCar);
		 }
		 
		 throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, null));
		
	}
	
	
	

	public DtoSaledCar toDto(SaledCar saledCar) {
		DtoSaledCar dtoSaledCar = new DtoSaledCar();
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoGallerist dtoGallerist = new DtoGallerist();
		DtoCar dtoCar = new DtoCar();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();

		BeanUtils.copyProperties(saledCar, dtoSaledCar);
		BeanUtils.copyProperties(saledCar.getCustomer(), dtoCustomer);
		BeanUtils.copyProperties(saledCar.getGallerist(), dtoGallerist);
		BeanUtils.copyProperties(saledCar.getCar(), dtoCar);
		BeanUtils.copyProperties(saledCar.getCustomer().getAddress(), dtoAddress);
		BeanUtils.copyProperties(saledCar.getCustomer().getAccount(), dtoAccount);

		// customer
		dtoCustomer.setDtoAddress(dtoAddress);
		dtoCustomer.setDtoAccount(dtoAccount);
		dtoSaledCar.setCreateTime(new Date());
		dtoSaledCar.setCustomer(dtoCustomer);

		// gallerist
		dtoGallerist.setCreateTime(new Date());
		dtoSaledCar.setGallerist(dtoGallerist);

		// car
		dtoCar.setCreateTime(new Date());
		dtoSaledCar.setCar(dtoCar);

		return dtoSaledCar;

	}
	
	public BigDecimal convertCustomerAmountToTL(Customer customer) {
		
	    // Fetches the current exchange rate
	    CurrencyRatesResponse currencyRatesResponse = currencyRatesService
	            .getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
	    BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

	    // Converts the customer's USD amount to TL
	    BigDecimal customerTLAmount = customer.getAccount().getAmount().multiply(usd);
	    return customerTLAmount;
	}

	public BigDecimal remainingCustomerAmountForUSD(Customer customer, Car car) {
		
	    //converting the car's TL price to USD
	    CurrencyRatesResponse currencyRatesResponse = currencyRatesService
	            .getCurrencyRates(DateUtils.getCurrentDate(new Date()), DateUtils.getCurrentDate(new Date()));
	    BigDecimal usd = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
	    
	    
	    BigDecimal carUSDPrice = car.getPrice().divide(usd, 2, RoundingMode.HALF_UP);

	    // Subtract the car's USD price from the customer's USD amount
	    BigDecimal remainingCustomerUSDAmount = customer.getAccount().getAmount().subtract(carUSDPrice);

	    return remainingCustomerUSDAmount;
	}

}
