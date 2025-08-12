package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;
import com.KayraAtalay.dto.DtoGalleristsCars;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestGalleristCarController {
	
	public RootEntity<DtoGalleristCar> saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
	
	public RootEntity<RestPageableEntity<DtoGalleristCar>> findAllPageable(PageableRequest pageableRequest);
	
	public RootEntity<DtoGalleristsCars> findGalleristCars(Long id);

}
