package com.KayraAtalay.controller;

import com.KayraAtalay.dto.CurrencyRatesResponse;

public interface IRestCurrencyRatesController {
	
	public RootEntity<CurrencyRatesResponse> getCurrencyRates(String startDate, String endDate);

}
