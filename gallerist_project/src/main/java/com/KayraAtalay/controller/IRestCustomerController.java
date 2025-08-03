package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;

public interface IRestCustomerController {
	
	public RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU dtoCustomerIU);

}
