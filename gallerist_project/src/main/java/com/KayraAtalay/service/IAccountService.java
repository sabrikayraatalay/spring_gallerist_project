package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;

public interface IAccountService {

	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU);
	
}

