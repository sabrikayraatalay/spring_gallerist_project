package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.repository.GalleristRepository;
import com.KayraAtalay.service.IGalleristService;

@Service
public class GalleristServiceImpl implements IGalleristService {
	
	@Autowired
	private GalleristRepository galleristRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Gallerist createGallerist(DtoGalleristIU dtoGalleristIU) {
		Optional<Address> optAddress = addressRepository.findById(dtoGalleristIU.getAddressId());
		if(optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, null));
		}
		
		Gallerist gallerist = new Gallerist();
		gallerist.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoGalleristIU, gallerist);
		gallerist.setAddress(optAddress.get());
		return gallerist;
	}

	@Override
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
		Gallerist savedGallerist = galleristRepository.save(createGallerist(dtoGalleristIU));
		
		DtoGallerist dtoGallerist = new DtoGallerist();
		DtoAddress dtoAddress = new DtoAddress();
		
		BeanUtils.copyProperties(savedGallerist, dtoGallerist);
		BeanUtils.copyProperties(savedGallerist.getAddress(), dtoAddress);
		
		dtoGallerist.setAddress(dtoAddress);
		
		return dtoGallerist;
	}

}
