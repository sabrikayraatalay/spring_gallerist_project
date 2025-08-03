package com.KayraAtalay.model;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.enums.CurrencyType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity {

	@Column(name = "license_plate")
	private String licensePlate;
	
	
	@Column(name = "brand")
	private String brand;
	
	
	@Column(name = "model")
	private String model;
	
	
	@Column(name = "production_year")
	private Integer productionYear;
	
	
	@Column(name = "price")
	private BigDecimal price;
	
	
	@Column(name = "currency_type")
	@Enumerated(EnumType.STRING)
	private CurrencyType currencyType;
	
	
	@Column(name = "damage_price")
	private BigDecimal damagePrice;
	
	
	@Column(name = "car_status_type")
	@Enumerated(EnumType.STRING)
	private CarStatusType carStatusType; 
	
}
