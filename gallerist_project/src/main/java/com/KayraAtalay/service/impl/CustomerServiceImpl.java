package com.KayraAtalay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public DtoCustomer findCustomerById(Long id) {

		Optional<Customer> optional = customerRepository.findById(id);

		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, null));
		}

		return toDto(optional.get());

	}

	@Override
	public Page<Customer> findAllPageable(Pageable pageable) {

		Page<Customer> page = customerRepository.findAll(pageable);

		return page;
	}

	private DtoCustomer toDto(Customer customer) {

		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();

		BeanUtils.copyProperties(customer, dtoCustomer);
		BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(customer.getAccount(), dtoAccount);

		dtoCustomer.setDtoAddress(dtoAddress);
		dtoCustomer.setDtoAccount(dtoAccount);

		return dtoCustomer;
	}

	@Override
	public List<DtoCustomer> toDtoList(List<Customer> customers) {

		if (customers.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}

		List<DtoCustomer> dtoCustomers = new ArrayList<>();

		for (Customer customer : customers) {
			DtoCustomer dtoCustomer = new DtoCustomer();
			DtoAddress dtoAddress = new DtoAddress();
			DtoAccount dtoAccount = new DtoAccount();

			BeanUtils.copyProperties(customer, dtoCustomer);
			BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
			BeanUtils.copyProperties(customer.getAccount(), dtoAccount);

			dtoCustomer.setDtoAddress(dtoAddress);
			dtoCustomer.setDtoAccount(dtoAccount);

			dtoCustomers.add(dtoCustomer);
		}

		return dtoCustomers;
	}

	@Override
	public DtoAccount findCustomerAccount(Long id) {

		Optional<Customer> optCustomer = customerRepository.findById(id);
		if (optCustomer.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, id.toString()));
		}

		DtoAccount dtoAccount = new DtoAccount();
		BeanUtils.copyProperties(optCustomer.get().getAccount(), dtoAccount);

		return dtoAccount;
	}

	@Override
	public DtoCustomer findCustomerByFirstName(String firstName) {

		Optional<Customer> optCustomer = customerRepository.findByFirstName(firstName);
		if (optCustomer.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, firstName));
		}

		return toDto(optCustomer.get());

	}

}
