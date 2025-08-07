package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAccountIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Account;
import com.KayraAtalay.repository.AccountRepository;
import com.KayraAtalay.service.IAccountService;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private AccountRepository accountRepository;

	private Account createAccount(DtoAccountIU dtoAccountIU) {
		Account account = new Account();
		account.setCreateTime(new Date());

		BeanUtils.copyProperties(dtoAccountIU, account);

		return account;
	}

	@Override
	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU) {
	
		String requestAccountNo = dtoAccountIU.getAccountNo();
		
		Optional<Account> optAccountNo = accountRepository.findByAccountNo(requestAccountNo);
		
		if(optAccountNo.equals(requestAccountNo)) {
			throw new BaseException(new ErrorMessage(MessageType.ACCOUNT_NO_ALREADY_EXISTS, dtoAccountIU.getAccountNo()));
		}
		
		Account savedAccount = accountRepository.save(createAccount(dtoAccountIU));
		
		DtoAccount dtoAccount = new DtoAccount();
		
		BeanUtils.copyProperties(savedAccount, dtoAccount);

		return dtoAccount;
	}

}
