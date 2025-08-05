package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.GalleristCar;
import com.KayraAtalay.repository.CarRepository;
import com.KayraAtalay.repository.GalleristCarRepository;
import com.KayraAtalay.repository.GalleristRepository;
import com.KayraAtalay.service.IGalleristCarService;

@Service
public class GalleristCarServiceImpl implements IGalleristCarService {

	@Autowired
	private GalleristCarRepository galleristCarRepository;

	@Autowired
	private GalleristRepository galleristRepository;

	@Autowired
	private CarRepository carRepository;

	private GalleristCar createGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {

		Optional<Gallerist> optGallerist = galleristRepository.findById(dtoGalleristCarIU.getGalleristId());
		if (optGallerist.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, dtoGalleristCarIU.getGalleristId().toString()));
		}

		Optional<Car> optCar = carRepository.findById(dtoGalleristCarIU.getCarId());
		if (optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, dtoGalleristCarIU.getCarId().toString()));
		}

		GalleristCar galleristCar = new GalleristCar();

		galleristCar.setCreateTime(new Date());
		galleristCar.setGallerist(optGallerist.get());
		galleristCar.setCar(optCar.get());

		return galleristCar;
	}

	@Override
	public DtoGalleristCar saveGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {
		GalleristCar savedGalleristCar = galleristCarRepository.save(createGalleristCar(dtoGalleristCarIU));

		DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
		DtoCar dtoCar = new DtoCar();
		DtoGallerist dtoGallerist = new DtoGallerist();
		
		DtoAddress dtoAddress = new DtoAddress();
		
		BeanUtils.copyProperties(savedGalleristCar.getGallerist().getAddress(), dtoAddress);
		dtoGallerist.setAddress(dtoAddress);

		BeanUtils.copyProperties(savedGalleristCar, dtoGalleristCar);
		BeanUtils.copyProperties(savedGalleristCar.getGallerist(), dtoGallerist);
		BeanUtils.copyProperties(savedGalleristCar.getCar(), dtoCar);

		dtoGallerist.setAddress(dtoAddress);

		dtoGalleristCar.setGallerist(dtoGallerist);
		dtoGalleristCar.setCar(dtoCar);

		return dtoGalleristCar;
	}

}
