package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;

public interface ICustomerService {
	
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);

}
