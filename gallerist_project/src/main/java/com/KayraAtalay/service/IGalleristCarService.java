package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;

public interface IGalleristCarService {
	
	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);

}
