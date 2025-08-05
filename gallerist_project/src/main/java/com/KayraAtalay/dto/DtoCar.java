package com.KayraAtalay.dto;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.enums.CurrencyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCar extends DtoBase {

	private String licensePlate;

	private String brand;

	private String model;

	private Integer productionYear;

	private BigDecimal price;

	private CurrencyType currencyType;

	private BigDecimal damagePrice;

	private CarStatusType carStatusType;

}
