package com.KayraAtalay.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.model.Customer;

public interface ICustomerService {
	
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public DtoCustomer findCustomerById(Long id);
	
	Page<Customer> findAllPageable(Pageable pageable);
	
	List<DtoCustomer> toDtoList(List<Customer> customers);

}
