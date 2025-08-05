package com.KayraAtalay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoGalleristCar extends DtoBase {
	
	private DtoGallerist gallerist;
	
	private DtoCar car;

}
