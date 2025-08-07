package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGalleristCarIU {
	
	@NotNull(message = "Gallerist ID cannot be null")
	private Long galleristId;
	
	@NotNull(message = "Car ID cannot be null")
	private Long carId;

}