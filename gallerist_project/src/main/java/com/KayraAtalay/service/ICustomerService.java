package com.KayraAtalay.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.model.Customer;

public interface ICustomerService {
	
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public DtoCustomer findCustomerById(Long id);
	
	public Page<Customer> findAllPageable(Pageable pageable);
	
	public DtoAccount findCustomerAccount(Long id);
	
	public List<DtoCustomer> findCustomerByFirstName(String firstName);
	
	public Page<DtoCustomer> findAllPageableDto(Pageable pageable);
	
	public DtoAddress updateCustomerAddress(Long customerId, DtoAddressIU dtoAddressIU);
	
	

}
