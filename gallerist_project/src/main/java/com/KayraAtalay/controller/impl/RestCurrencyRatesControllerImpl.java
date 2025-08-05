package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCurrencyRatesController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.CurrencyRatesResponse;
import com.KayraAtalay.service.ICurrencyRatesService;

@RestController
@RequestMapping("/rest/api/currency-rates")
public class RestCurrencyRatesControllerImpl extends RestBaseController implements IRestCurrencyRatesController {
	
	@Autowired
	private ICurrencyRatesService currencyRatesService;

	@GetMapping("")
	@Override
	public RootEntity<CurrencyRatesResponse> getCurrencyRates(@RequestParam("startDate") String startDate,
															  @RequestParam("endDate") String endDate) {
		return ok(currencyRatesService.getCurrencyRates(startDate, endDate));
	}

}
