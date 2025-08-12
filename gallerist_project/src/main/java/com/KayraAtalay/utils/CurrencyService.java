package com.KayraAtalay.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.CurrencyRatesResponse;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.service.ICurrencyRatesService;

@Service
public class CurrencyService {

	@Autowired
	private ICurrencyRatesService currencyRatesService;

	// Helper method to fetch current currency rates
	private CurrencyRatesResponse getCurrencyRatesResponse() {
		return currencyRatesService.getCurrencyRates(DateUtils.getCurrentDate(new Date()),
				DateUtils.getCurrentDate(new Date()));
	}

	// Converts a customer's TL amount to USD
	public BigDecimal convertCustomerAmountToUSD(Customer customer) {
		CurrencyRatesResponse currencyRatesResponse = getCurrencyRatesResponse();
		BigDecimal usdRate = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		return customer.getAccount().getAmount().divide(usdRate, 2, RoundingMode.HALF_UP);
	}

	// Converts a customer's USD amount to TL
	public BigDecimal convertCustomerAmountToTL(Customer customer) {
		CurrencyRatesResponse currencyRatesResponse = getCurrencyRatesResponse();
		BigDecimal usdRate = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
		return customer.getAccount().getAmount().multiply(usdRate);
	}

	// Calculates remaining balance for a customer with a TL account buying a
	// USD-priced car
	public BigDecimal remainingCustomerAmountAfterTLtoUSD(Customer customer, Car car) {
		BigDecimal customerUSDAmount = convertCustomerAmountToUSD(customer);
		BigDecimal remainingCustomerUSDAmount = customerUSDAmount.subtract(car.getPrice());

		CurrencyRatesResponse currencyRatesResponse = getCurrencyRatesResponse();
		BigDecimal usdRate = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

		return remainingCustomerUSDAmount.multiply(usdRate);
	}

	// Calculates remaining balance for a customer with a USD account buying a
	// TL-priced car
	public BigDecimal remainingCustomerAmountAfterUSDtoTL(Customer customer, Car car) {
		CurrencyRatesResponse currencyRatesResponse = getCurrencyRatesResponse();
		BigDecimal usdRate = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());

		BigDecimal carUSDPrice = car.getPrice().divide(usdRate, 2, RoundingMode.HALF_UP);
		return customer.getAccount().getAmount().subtract(carUSDPrice);
	}

	// Checks if the customer's balance is sufficient for the car purchase, handling
	// currency conversions
	public boolean checkAmount(Customer customer, Car car) {
		if (customer.getAccount().getCurrencyType().equals(car.getCurrencyType())) {
			return customer.getAccount().getAmount().compareTo(car.getPrice()) >= 0;
		} else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("TL")
				&& car.getCurrencyType().name().equalsIgnoreCase("USD")) {
			BigDecimal customerUSDAmount = convertCustomerAmountToUSD(customer);
			return customerUSDAmount.compareTo(car.getPrice()) >= 0;
		} else if (customer.getAccount().getCurrencyType().name().equalsIgnoreCase("USD")
				&& car.getCurrencyType().name().equalsIgnoreCase("TL")) {
			CurrencyRatesResponse currencyRatesResponse = getCurrencyRatesResponse();
			BigDecimal usdRate = new BigDecimal(currencyRatesResponse.getItems().get(0).getUsd());
			BigDecimal carUSDPrice = car.getPrice().divide(usdRate, 2, RoundingMode.HALF_UP);
			return customer.getAccount().getAmount().compareTo(carUSDPrice) >= 0;
		}
		return false;
	}
}