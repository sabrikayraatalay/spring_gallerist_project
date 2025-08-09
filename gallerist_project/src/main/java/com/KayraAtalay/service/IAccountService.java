package com.KayraAtalay.service;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;
import com.KayraAtalay.dto.LoadMoneyRequest;

public interface IAccountService {

	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU);
	
	public DtoAccount loadMoneyToAccount(LoadMoneyRequest request);
	
}

