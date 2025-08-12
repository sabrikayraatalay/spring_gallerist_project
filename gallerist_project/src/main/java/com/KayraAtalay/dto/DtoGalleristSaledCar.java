package com.KayraAtalay.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoGalleristSaledCar {
	
	private DtoGallerist gallerist;
	
	private List<DtoSaleDetail> sales;
	

}
