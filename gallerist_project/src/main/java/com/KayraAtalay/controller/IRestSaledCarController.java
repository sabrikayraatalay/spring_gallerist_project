package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoSaledCar;
import com.KayraAtalay.dto.DtoSaledCarIU;

public interface IRestSaledCarController {
	
	public RootEntity<DtoSaledCar> buyCar(DtoSaledCarIU dtoSaledCarIU);

}
