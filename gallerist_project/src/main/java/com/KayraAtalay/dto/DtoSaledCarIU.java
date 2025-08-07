package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoSaledCarIU {
	
	@NotNull(message = "Customer ID cannot be null")
	private Long customerId;
	
	@NotNull(message = "Gallerist ID cannot be null")
	private Long galleristId;
	
	@NotNull(message = "Car ID cannot be null")
	private Long carId;

}