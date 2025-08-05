package com.KayraAtalay.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.KayraAtalay.dto.CurrencyRatesResponse;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.service.ICurrencyRatesService;

@Service
public class CurrencyRatesServiceImpl implements ICurrencyRatesService {

	@Override
	public CurrencyRatesResponse getCurrencyRates(String startDate, String endDate) {
		String rootUrl = "https://evds2.tcmb.gov.tr/service/evds/";
		String series = "TP.DK.USD.A";
		String type = "json";

		String endpoint = rootUrl + "series=" + series + "&startDate=" + startDate + "&endDate=" + endDate + "&type="
				+ type;

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("key", "U3XKU5rqfu");

		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<CurrencyRatesResponse> response = restTemplate.exchange(endpoint, HttpMethod.GET, httpEntity,
					new ParameterizedTypeReference<CurrencyRatesResponse>() {
					});

			if (response.getStatusCode().is2xxSuccessful()) {
				return response.getBody();
			}
		} catch (Exception e) {
			throw new BaseException(new ErrorMessage(MessageType.CURRENCY_RATES_IS_OCCURED, e.getMessage()));
		}

		// https://evds2.tcmb.gov.tr/service/evds/series=TP.DK.USD.A&startDate=05-08-2025&endDate=05-08-2025&type=json
		
		return null;
		
	}

}
