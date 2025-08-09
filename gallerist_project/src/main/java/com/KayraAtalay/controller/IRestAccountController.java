package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;
import com.KayraAtalay.dto.LoadMoneyRequest;

public interface IRestAccountController {
	
	public RootEntity<DtoAccount> saveAccount(DtoAccountIU dtoAccountIU);
	
	public RootEntity<DtoAccount> loadMoneyToAccount(LoadMoneyRequest request);

}
