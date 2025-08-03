package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;

public interface IRestAddressController {
	
	public RootEntity<DtoAddress> saveAddress(DtoAddressIU dtoAddressIU);

}
