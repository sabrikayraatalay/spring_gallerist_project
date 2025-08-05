package com.KayraAtalay.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRatesResponse {
	
	private Integer totalCount;
	
	private List<CurrencyRatesItems> items;

}
