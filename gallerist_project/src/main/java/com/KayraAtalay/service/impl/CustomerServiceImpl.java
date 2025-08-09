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
import com.KayraAtalay.dto.DtoAddressIU;
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

    // --- PRIVATE HELPER METHODS ---

	private Customer createCustomer(DtoCustomerIU dtoCustomerIU) {
		Optional<Address> optAddress = addressRepository.findById(dtoCustomerIU.getAddressId());
		if (optAddress.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.ADDRESS_NOT_FOUND, dtoCustomerIU.getAddressId().toString()));
		}

		Optional<Account> optAccount = accountRepository.findById(dtoCustomerIU.getAccountId());
		if (optAccount.isEmpty()) {
			throw new BaseException(
					new ErrorMessage(MessageType.ACCOUNT_NOT_FOUND, dtoCustomerIU.getAccountId().toString()));
		}

		Customer customer = new Customer();
		customer.setCreateTime(new Date());

		BeanUtils.copyProperties(dtoCustomerIU, customer);
		customer.setAddress(optAddress.get());
		customer.setAccount(optAccount.get());

		return customer;
	}

	private DtoCustomer toDto(Customer customer) {
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();

		BeanUtils.copyProperties(customer, dtoCustomer);
		BeanUtils.copyProperties(customer.getAddress(), dtoAddress);
		BeanUtils.copyProperties(customer.getAccount(), dtoAccount);

		dtoCustomer.setCreateTime(customer.getCreateTime());
		dtoCustomer.setDtoAddress(dtoAddress);
		dtoCustomer.setDtoAccount(dtoAccount);

		return dtoCustomer;
	}

	private List<DtoCustomer> toDtoList(List<Customer> customers) {
		if (customers.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
		}

		List<DtoCustomer> dtoCustomers = new ArrayList<>();

		for (Customer customer : customers) {
			dtoCustomers.add(toDto(customer));
		}

		return dtoCustomers;
	}
    
    // --- PUBLIC SERVICE METHODS ---

	@Override
	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU) {
		Optional<Customer> optCustomer = customerRepository.findByTckn(dtoCustomerIU.getTckn());
		if (optCustomer.isPresent()) {
			throw new BaseException(new ErrorMessage(MessageType.TCKN_ALREADY_EXISTS, dtoCustomerIU.getTckn()));
		}

		Customer savedCustomer = customerRepository.save(createCustomer(dtoCustomerIU));
		return toDto(savedCustomer);
	}

	@Override
	public DtoCustomer findCustomerById(Long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, id.toString()));
		}

		return toDto(optional.get());
	}

	@Override
	public Page<Customer> findAllPageable(Pageable pageable) {
		Page<Customer> page = customerRepository.findAll(pageable);
		return page;
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
	public List<DtoCustomer> findCustomerByFirstName(String firstName) {
		return toDtoList(customerRepository.findByFirstName(firstName));
	}

	@Override
	public Page<DtoCustomer> findAllPageableDto(Pageable pageable) {
		Page<Customer> customerPage = customerRepository.findAll(pageable);

	    if (customerPage.isEmpty()) {
	        throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, null));
	    }

	    List<DtoCustomer> dtoCustomers = new ArrayList<>();
	    for (Customer customer : customerPage.getContent()) {
	        dtoCustomers.add(toDto(customer));
	    }

	    return new org.springframework.data.domain.PageImpl<>(
	        dtoCustomers, 
	        pageable, 
	        customerPage.getTotalElements()
	    );
	}

	@Override
	public DtoAddress updateCustomerAddress(Long customerId, DtoAddressIU dtoAddressIU) {
		
		Optional<Customer> optCustomer = customerRepository.findById(customerId);
		if (optCustomer.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_NOT_FOUND, customerId.toString()));
		}
		
		
		Customer customer = optCustomer.get();
		Address addressToUpdate = customer.getAddress();
		BeanUtils.copyProperties(dtoAddressIU, addressToUpdate);
		DtoAddress dtoAddress = new DtoAddress();
		BeanUtils.copyProperties(addressToUpdate, dtoAddress);
		
		Address updatedAddress = addressRepository.save(addressToUpdate);
		
		
		customer.setAddress(updatedAddress);
		
		customerRepository.save(customer);
		
		return dtoAddress;
	}

	

}