package com.KayraAtalay.dto;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CurrencyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAccount extends DtoBase {
	
	private String accountNo;
	
	private String iban;
	
	private BigDecimal amount; 
	
	private CurrencyType currencyType;



}
