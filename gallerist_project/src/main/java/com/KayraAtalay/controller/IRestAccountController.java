package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;

public interface IRestAccountController {
	
	public RootEntity<DtoAccount> saveAccount(DtoAccountIU dtoAccountIU);

}
