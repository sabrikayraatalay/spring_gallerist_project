package com.KayraAtalay.service;

import com.KayraAtalay.dto.CurrencyRatesResponse;

public interface ICurrencyRatesService {
	
	public CurrencyRatesResponse getCurrencyRates(String startDate, String endDate);

}
