package com.KayraAtalay.controller;

import java.math.BigDecimal;
import java.util.List;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestCarController {
	
	public RootEntity<DtoCar> saveCar(DtoCarIU dtoCarIU);
	
	public RootEntity<RestPageableEntity<DtoCar>> findAllPageable(PageableRequest pageableRequest);
	
	public RootEntity<DtoCar> findCarById(Long id);
	
	public RootEntity<List<DtoCar>> findCarByBrand(String brand);
	
	public RootEntity<DtoCar> updateCarPrice(Long id, BigDecimal price);

}
