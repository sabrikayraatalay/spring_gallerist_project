package com.KayraAtalay.dto;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CurrencyType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAccountIU {
	
	@NotEmpty
	private String accountNo;
	
	@NotEmpty
	private String iban;
	
	@NotNull
	private BigDecimal amount; //BigDecimal class for money transactions
	
	@NotNull
	private CurrencyType currencyType;


}
