package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestCustomerController {
	
	public RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU dtoCustomerIU);
	
	public RootEntity<DtoCustomer> findCustomerById(Long id);
	
	public RootEntity<RestPageableEntity<DtoCustomer>> findAllPageable(PageableRequest pageableRequest);

}
