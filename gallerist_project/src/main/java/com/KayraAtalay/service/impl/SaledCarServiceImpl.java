package com.KayraAtalay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoGalleristSaledCar;
import com.KayraAtalay.dto.DtoSaleDetail;
import com.KayraAtalay.dto.DtoSaledCar;
import com.KayraAtalay.dto.DtoSaledCarIU;
import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.GalleristCar;
import com.KayraAtalay.model.SaledCar;
import com.KayraAtalay.repository.CarRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.repository.GalleristCarRepository;
import com.KayraAtalay.repository.GalleristRepository;
import com.KayraAtalay.repository.SaledCarRepository;
import com.KayraAtalay.service.ISaledCarService;
import com.KayraAtalay.utils.CurrencyService;
import com.KayraAtalay.utils.DtoConverter;

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
	private CurrencyService currencyService;

	@Autowired
	private GalleristCarRepository galleristCarRepository;

	private boolean checkGalleristCarOwnership(Long galleristId, Long carId) {
		Optional<GalleristCar> optGalleristCar = galleristCarRepository.findByGalleristIdAndCarId(galleristId, carId);
		return optGalleristCar.isPresent();
	}

	private boolean checkCarStatus(Long carId) {
		Optional<Car> optCar = carRepository.findById(carId);
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, carId.toString()));
		}
		return optCar.get().getCarStatusType().name().equalsIgnoreCase("SALABLE");
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
		if (!checkGalleristCarOwnership(dtoSaledCarIU.getGalleristId(), dtoSaledCarIU.getCarId())) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_IS_NOT_BELONG_TO_GALLERIST, null));
		}

		if (!checkCarStatus(dtoSaledCarIU.getCarId())) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_IS_ALREADY_SOLD, null));
		}

		SaledCar savedSaledCar = saledCarRepository.save(createSaledCar(dtoSaledCarIU));
		Customer customer = savedSaledCar.getCustomer();
		Car car = savedSaledCar.getCar();

		if (!currencyService.checkAmount(customer, car)) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_AMOUNT_IS_NOT_ENOUGH, null));
		}

		// Same currency transaction
		if (customer.getAccount().getCurrencyType().equals(car.getCurrencyType())) {
			BigDecimal newCustomerAmount = customer.getAccount().getAmount().subtract(car.getPrice());

			car.setCarStatusType(CarStatusType.SOLD);
			carRepository.save(car);

			customer.getAccount().setAmount(newCustomerAmount);
			customerRepository.save(customer);

			return DtoConverter.toDto(savedSaledCar);
		}
		// Customer is TL, car price is USD
		else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("TL")
				&& car.getCurrencyType().name().equalsIgnoreCase("USD")) {

			car.setCarStatusType(CarStatusType.SOLD);
			carRepository.save(car);

			customer.getAccount().setAmount(currencyService.remainingCustomerAmountAfterTLtoUSD(customer, car));
			customerRepository.save(customer);

			return DtoConverter.toDto(savedSaledCar);
		}
		// Customer is USD, car price is TL
		else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("USD")
				&& car.getCurrencyType().name().equalsIgnoreCase("TL")) {

			car.setCarStatusType(CarStatusType.SOLD);
			carRepository.save(car);

			customer.getAccount().setAmount(currencyService.remainingCustomerAmountAfterUSDtoTL(customer, car));
			customerRepository.save(customer);

			return DtoConverter.toDto(savedSaledCar);
		}

		throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, null));
	}

	@Override
	public DtoGalleristSaledCar findGalleristSales(Long id) {
		Optional<Gallerist> optGallerist = galleristRepository.findById(id);
		if (optGallerist.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, id.toString()));
		}

		Gallerist gallerist = optGallerist.get();

		List<SaledCar> saledCars = saledCarRepository.findByGallerist(gallerist);

		if (saledCars.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.SALES_NOT_FOUND,
					"No sales found for gallerist with id: " + id.toString()));
		}

		// SaledCar to DtoSaleDetail
		List<DtoSaleDetail> saleDetails = saledCars.stream().map(saledCar -> {
			DtoSaleDetail dtoSaleDetail = new DtoSaleDetail();
			dtoSaleDetail.setCustomer(DtoConverter.toDto(saledCar.getCustomer()));
			dtoSaleDetail.setCar(DtoConverter.toDto(saledCar.getCar()));
			return dtoSaleDetail;
		}).collect(Collectors.toList());

		DtoGalleristSaledCar dtoGalleristSaledCar = new DtoGalleristSaledCar();
		dtoGalleristSaledCar.setGallerist(DtoConverter.toDto(gallerist));
		dtoGalleristSaledCar.setSales(saleDetails);

		return dtoGalleristSaledCar;
	}

}
