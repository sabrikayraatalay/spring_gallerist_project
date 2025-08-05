package com.KayraAtalay.dto;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CurrencyType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCarIU {

	@NotEmpty
	private String licensePlate;

	@NotEmpty
	private String brand;

	@NotEmpty
	private String model;

	@NotNull
	private Integer productionYear;

	@NotNull
	private BigDecimal price;

	@NotNull
	private CurrencyType currencyType;

	@NotNull
	private BigDecimal damagePrice;

}
