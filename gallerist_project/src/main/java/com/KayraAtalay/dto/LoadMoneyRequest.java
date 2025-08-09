package com.KayraAtalay.dto;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class LoadMoneyRequest {
	
	private String iban;
	
	private BigDecimal amount;

}
