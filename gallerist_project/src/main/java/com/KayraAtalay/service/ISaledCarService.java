package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoGalleristSaledCar;
import com.KayraAtalay.dto.DtoSaledCar;
import com.KayraAtalay.dto.DtoSaledCarIU;

public interface ISaledCarService {
	
	public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU);
	
	public DtoGalleristSaledCar findGalleristSales(Long id);

}
