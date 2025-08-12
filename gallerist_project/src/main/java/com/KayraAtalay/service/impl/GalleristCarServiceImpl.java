package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;
import com.KayraAtalay.dto.DtoGalleristsCars;
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
import com.KayraAtalay.utils.DtoConverter;

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
			throw new BaseException(
					new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, dtoGalleristCarIU.getGalleristId().toString()));
		}

		Optional<Car> optCar = carRepository.findById(dtoGalleristCarIU.getCarId());
		if (optCar.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.CAR_NOT_FOUND, dtoGalleristCarIU.getCarId().toString()));
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

		return DtoConverter.toDto(savedGalleristCar);
	}

	@Override
	public Page<GalleristCar> findAllPageable(Pageable pageable) {
		return galleristCarRepository.findAll(pageable);
	}

	@Override
	public Page<DtoGalleristCar> findAllPageableDto(Pageable pageable) {

		Page<GalleristCar> galleristCarpage = galleristCarRepository.findAll(pageable);
		if (galleristCarpage.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GALLERISTCAR_NOT_FOUND, null));
		}

		return galleristCarpage.map(DtoConverter::toDto);
	}

	@Override
	public DtoGalleristsCars findGalleristCars(Long id) {
	    Optional<Gallerist> optGallerist = galleristRepository.findById(id);
	    if (optGallerist.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, id.toString()));
	    }
	    
	    Gallerist gallerist = optGallerist.get();
	    
	    List<GalleristCar> galleristCars = galleristCarRepository.findByGallerist(gallerist);

	    if (galleristCars.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.CAR_NOT_FOUND, "There is no car that belongs to this gallerist"));
	    }
	    
	    List<DtoCar> dtoCars = galleristCars.stream()
	                                        .map(galleristCar -> DtoConverter.toDto(galleristCar.getCar()))
	                                        .collect(Collectors.toList());

	    DtoGalleristsCars dtoGalleristsCars = new DtoGalleristsCars();
	    dtoGalleristsCars.setGallerist(DtoConverter.toDto(gallerist));
	    dtoGalleristsCars.setCars(dtoCars);

	    return dtoGalleristsCars;
	}

}
