package com.KayraAtalay.dto;

import java.math.BigDecimal;
import com.KayraAtalay.enums.CurrencyType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoAccountIU {

	@NotEmpty(message = "Account number cannot be empty")
	@Size(min = 10, max = 10, message = "Account number must be 10 digits long")
	@Pattern(regexp = "^[0-9]+$", message = "Account number must contain only digits")
	private String accountNo;
	
	@NotEmpty(message = "IBAN cannot be empty")
	@Size(min = 26, max = 26, message = "IBAN must be 26 characters long")
	@Pattern(regexp = "^TR[0-9]{24}$", message = "Invalid IBAN format")
	private String iban;
	
	@NotNull(message = "Amount cannot be null")
	@PositiveOrZero(message = "Amount cannot be negative")
	private BigDecimal amount;
	
	@NotNull(message = "Currency type cannot be null")
	private CurrencyType currencyType;

}