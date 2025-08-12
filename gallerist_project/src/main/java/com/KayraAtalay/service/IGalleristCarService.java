package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;
import com.KayraAtalay.dto.DtoGalleristsCars;
import com.KayraAtalay.model.GalleristCar;

public interface IGalleristCarService {
	
	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
	
	public Page<GalleristCar> findAllPageable(Pageable pageable);
	
	public Page<DtoGalleristCar> findAllPageableDto(Pageable pageable);
	
	public DtoGalleristsCars findGalleristCars(Long id);

}
