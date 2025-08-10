package com.KayraAtalay.utils;

import org.springframework.beans.BeanUtils;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.model.Account;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Gallerist;

public class DtoConverter {

	public static DtoAddress toDto(Address address) {
	    DtoAddress dtoAddress = new DtoAddress();
	    BeanUtils.copyProperties(address, dtoAddress);
	    return dtoAddress;
	}

	public static DtoAccount toDto(Account account) {
		DtoAccount dtoAccount = new DtoAccount();
		BeanUtils.copyProperties(account, dtoAccount);
		return dtoAccount;
	}

	public static DtoCar toDto(Car car) {
		DtoCar dtoCar = new DtoCar();
		BeanUtils.copyProperties(car, dtoCar);
		return dtoCar;
	}

	public static DtoCustomer toDto(Customer customer) {
		DtoCustomer dtoCustomer = new DtoCustomer();
		BeanUtils.copyProperties(customer, dtoCustomer);

		dtoCustomer.setDtoAddress(toDto(customer.getAddress()));
		dtoCustomer.setDtoAccount(toDto(customer.getAccount()));

		return dtoCustomer;
	}

	public static DtoGallerist toDto(Gallerist gallerist) {
		DtoGallerist dtoGallerist = new DtoGallerist();
		BeanUtils.copyProperties(gallerist, dtoGallerist);

		dtoGallerist.setAddress(toDto(gallerist.getAddress()));

		return dtoGallerist;
	}
}