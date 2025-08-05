package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;

public interface IRestGalleristCarController {
	
	public RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);

}
