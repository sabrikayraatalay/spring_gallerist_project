package com.KayraAtalay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;
import com.KayraAtalay.enums.CarStatusType;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.repository.CarRepository;
import com.KayraAtalay.service.ICarService;
import com.KayraAtalay.utils.DtoConverter;

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
		return DtoConverter.toDto(savedCar);
	}

	@Override
	public Page<Car> findAllPageable(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	@Override
	public Page<DtoCar> findAllPageableDto(Pageable pageable) {
		Page<Car> carPage = carRepository.findAll(pageable);
		if (carPage.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}
		
		return carPage.map(DtoConverter::toDto);
	}

	@Override
	public DtoCar findCarById(Long id) {
		Optional<Car> optCar = carRepository.findById(id);
		if(optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, id.toString()));
		}
		return DtoConverter.toDto(optCar.get());
	}

	@Override
	public DtoCar updateCarPrice(Long carId, BigDecimal price) {
		Optional<Car> optCar = carRepository.findById(carId);
		if(optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, carId.toString()));
		}
		
		if(optCar.get().getCarStatusType().name().equals("SOLD")) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_IS_ALREADY_SOLD, carId.toString()));
		}
		Car car = optCar.get();
		car.setPrice(price);
		Car savedCar = carRepository.save(car);
		
		return DtoConverter.toDto(savedCar);
	}

	@Override
	public List<DtoCar> findCarByBrand(String brand) {
		List<Car> optBrandCars = carRepository.findByBrand(brand);
		if(optBrandCars.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, brand));
		}
		
		List<DtoCar> dtoCars = new ArrayList<>();
		for (Car car : optBrandCars) {
			DtoCar dtoCar = DtoConverter.toDto(car);
			dtoCars.add(dtoCar);
		}
		return dtoCars;
	}
}