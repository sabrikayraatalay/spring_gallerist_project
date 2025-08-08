package com.KayraAtalay.controller;

import java.util.List;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestCustomerController {
	
	public RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public RootEntity<DtoCustomer> findCustomerById(Long id);
	
	public RootEntity<RestPageableEntity<DtoCustomer>> findAllPageable(PageableRequest pageableRequest);
	
	public RootEntity<DtoAccount> findCustomerAccount(Long id);
	
	public RootEntity<List<DtoCustomer>> findCustomerByFirstName(String firstName);
	
	public RootEntity<DtoAddress> updateCustomerAddress(Long id, DtoAddressIU dtoAddressIU);

}
