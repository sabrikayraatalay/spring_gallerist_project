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
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.service.IAddressService;

@Service
public class AddressServiceImpl implements IAddressService{
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Address createAddress(DtoAddressIU dtoAddressIU) {
		Address address = new Address();
		address.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoAddressIU, address);
		
		return address;
	}
	
	private DtoAddress toDto(Address address) {
		DtoAddress dtoAddress = new DtoAddress();
		
		BeanUtils.copyProperties(address, dtoAddress);
		
		return dtoAddress;
	}

	@Override
	public DtoAddress saveAddress(DtoAddressIU dtoAddressIU) {
		DtoAddress dtoAddress = new DtoAddress();
		Address savedAddress = addressRepository.save(createAddress(dtoAddressIU));
		
		BeanUtils.copyProperties(savedAddress, dtoAddress);
		return dtoAddress;
	}

	@Override
	public Page<Address> findAllPageable(Pageable pageable) {
		Page<Address> page = addressRepository.findAll(pageable);
		return page;
	}

	@Override
	public Page<DtoAddress> findAllPageableDto(Pageable pageable) {
		Page<Address> addressPage = addressRepository.findAll(pageable);

	    if (addressPage.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
	    }

	    List<DtoAddress> dtoAddresses = new ArrayList<>();
	    for (Address address : addressPage.getContent()) {
	        dtoAddresses.add(toDto(address));
	    }

	    return new org.springframework.data.domain.PageImpl<>(
	        dtoAddresses, 
	        pageable, 
	        addressPage.getTotalElements()
	    );
	}

	@Override
	public DtoAddress findAddressById(Long id) {
		Optional<Address> optAddress = addressRepository.findById(id);
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, id.toString()));
		}
		
		return toDto(optAddress.get());
	}

	@Override
	public DtoAddress deleteAddressById(Long id) {
		Optional<Address> optAddress = addressRepository.findById(id);
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, id.toString()));
		}
		addressRepository.delete(optAddress.get());
		return toDto(optAddress.get());
	}

}
