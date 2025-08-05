package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;

public interface IRestCarController {
	
	public RootEntity<DtoCar> saveCar(DtoCarIU dtoCarIU);

}
