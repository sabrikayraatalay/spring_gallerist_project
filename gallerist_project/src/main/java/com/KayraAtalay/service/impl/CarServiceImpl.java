package com.KayraAtalay.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;
import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.repository.CarRepository;
import com.KayraAtalay.service.ICarService;

@Service
public class CarServiceImpl implements ICarService {
	
	@Autowired
	private CarRepository carRepository;
	
	private Car createCar(DtoCarIU dtoCarIU) {
		Car car = new Car();
		car.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoCarIU, car);
		car.setCarStatusType(CarStatusType.SALABLE);
		
		return car;
	}

	@Override
	public DtoCar saveCar(DtoCarIU dtoCarIU) {
		Car savedCar = carRepository.save(createCar(dtoCarIU));
		DtoCar dtoCar = new DtoCar();
		
		BeanUtils.copyProperties(savedCar, dtoCar);
		
		return dtoCar;
	}

}
