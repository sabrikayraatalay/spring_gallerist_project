package com.KayraAtalay.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.exception.BaseException;
import com.KayraAtalay.exception.ErrorMessage;
import com.KayraAtalay.exception.MessageType;
import com.KayraAtalay.model.Account;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.repository.AccountRepository;
import com.KayraAtalay.repository.AddressRepository;
import com.KayraAtalay.repository.CustomerRepository;
import com.KayraAtalay.service.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AccountRepository accountRepository;

	private Customer createCustomer(DtoCustomerIU dtoCustomerIU) {

		// Checking addressId and accountId in request
		Optional<Address> optAddress = addressRepository.findById(dtoCustomerIU.getAddressId());
		if (optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, null));
		}

		Optional<Account> optAccount = accountRepository.findById(dtoCustomerIU.getAccountId());
		if (optAccount.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.ACCOUNT_NOT_FOUND, null));
		}

		Customer customer = new Customer();
		customer.setCreateTime(new Date());

		BeanUtils.copyProperties(dtoCustomerIU, customer);
		customer.setAddress(optAddress.get());
		customer.setAccount(optAccount.get());

		return customer;
	}

	@Override
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
		
		Optional<Customer> optCustomer = customerRepository.findByTckn(dtoCustomerIU.getTckn());
		if (optCustomer.isPresent()) {
			throw new BaseException(new ErrorMessage(MessageType.TCKN_ALREADY_EXISTS, null));
		}
		
		Customer savedCustomer = customerRepository.save(createCustomer(dtoCustomerIU));
		
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();
		
		BeanUtils.copyProperties(savedCustomer, dtoCustomer);
		BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);
		
		dtoCustomer.setDtoAddress(dtoAddress);
		dtoCustomer.setDtoAccount(dtoAccount);
		
		
		return dtoCustomer;
	}

}
