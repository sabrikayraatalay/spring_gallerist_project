package com.KayraAtalay.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;
import com.KayraAtalay.model.Car;

public interface ICarService {
	
	public DtoCar saveCar(DtoCarIU dtoCarIU);
	
	public Page<Car> findAllPageable(Pageable pageable);
	
	public Page<DtoCar> findAllPageableDto(Pageable pageable);
	
	public DtoCar findCarById(Long id);
	
	public List<DtoCar> findCarByBrand(String brand);
	
	public DtoCar updateCarPrice(Long carId, BigDecimal price);

}
