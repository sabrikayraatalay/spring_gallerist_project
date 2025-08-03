package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestAccountController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;
import com.KayraAtalay.service.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/account")
public class RestAccountControllerImpl extends RestBaseController implements IRestAccountController {
	
	@Autowired
	private IAccountService accountService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoAccount> saveAccount(@Valid @RequestBody DtoAccountIU dtoAccountIU) {
		return ok(accountService.saveAccount(dtoAccountIU));
	}

}
