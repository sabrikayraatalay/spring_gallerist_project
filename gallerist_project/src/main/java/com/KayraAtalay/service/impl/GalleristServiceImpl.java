package com.KayraAtalay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
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
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, dtoGalleristIU.getAddressId().toString()));
		}
		
		Gallerist gallerist = new Gallerist();
		gallerist.setCreateTime(new Date());
		BeanUtils.copyProperties(dtoGalleristIU, gallerist);
		gallerist.setAddress(optAddress.get());
		return gallerist;
	}
	
	private DtoGallerist toDto(Gallerist gallerist) {
		DtoGallerist dtoGallerist = new DtoGallerist();
		DtoAddress dtoAddress = new DtoAddress();
		
		BeanUtils.copyProperties(gallerist, dtoGallerist);
		BeanUtils.copyProperties(gallerist.getAddress(), dtoAddress);
		
		dtoGallerist.setAddress(dtoAddress);
		
		return dtoGallerist;
	}

	@Override
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU) {
		Gallerist savedGallerist = galleristRepository.save(createGallerist(dtoGalleristIU));
		
	
		return toDto(savedGallerist);
	}

	@Override
	public Page<Gallerist> findAllPageable(Pageable pageable) {
		Page<Gallerist> page = galleristRepository.findAll(pageable);
		return page;
	}

	@Override
	public Page<DtoGallerist> findAllPageableDto(Pageable pageable) {
	    Page<Gallerist> galleristPage = galleristRepository.findAll(pageable);

	    if (galleristPage.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
	    }

	    List<DtoGallerist> dtoGallerists = new ArrayList<>();
	    for (Gallerist gallerist : galleristPage.getContent()) {
	        dtoGallerists.add(toDto(gallerist)); 
	    }

	    return new org.springframework.data.domain.PageImpl<>(
	        dtoGallerists, 
	        pageable, 
	        galleristPage.getTotalElements()
	    );
	}

	@Override
	public DtoGallerist findGalleristById(Long id) {
		
		Optional<Gallerist> optGallerist = galleristRepository.findById(id);
		if (optGallerist.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, id.toString()));
		}
		
		return toDto(optGallerist.get());
	}

	@Override
	public DtoAddress updateGalleristAddress(Long galleristId, DtoAddressIU dtoAddressIU) {
		
		   Optional<Gallerist> optGallerist = galleristRepository.findById(galleristId);
		    if (optGallerist.isEmpty()) {
		        throw new BaseException(new ErrorMessage(MessageType.GALLERIST_NOT_FOUND, galleristId.toString()));
		    }

		    Gallerist gallerist = optGallerist.get();
		    Address addressToUpdate = gallerist.getAddress();
		    
		    BeanUtils.copyProperties(dtoAddressIU, addressToUpdate);
		    
		    Address updatedAddress = addressRepository.save(addressToUpdate);
		    
		    gallerist.setAddress(updatedAddress);
		    galleristRepository.save(gallerist);
		    
		    DtoAddress dtoAddress = new DtoAddress();
		    BeanUtils.copyProperties(updatedAddress, dtoAddress);
		    
		    return dtoAddress;
		}

}
