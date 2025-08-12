package com.KayraAtalay.utils;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoSaledCar;
import com.KayraAtalay.model.Account;
import com.KayraAtalay.model.Address;
import com.KayraAtalay.model.Car;
import com.KayraAtalay.model.Customer;
import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.GalleristCar;
import com.KayraAtalay.model.SaledCar;

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

	public static DtoGalleristCar toDto(GalleristCar galleristCar) {
		DtoGalleristCar dtoGalleristCar = new DtoGalleristCar();
		Gallerist gallerist = galleristCar.getGallerist();
		Car car = galleristCar.getCar();

		BeanUtils.copyProperties(galleristCar, dtoGalleristCar);

		dtoGalleristCar.setGallerist(toDto(gallerist));
		dtoGalleristCar.setCar(toDto(car));

		return dtoGalleristCar;

	}
	
	public static DtoSaledCar toDto(SaledCar saledCar) {
		DtoSaledCar dtoSaledCar = new DtoSaledCar();
		DtoCustomer dtoCustomer = new DtoCustomer();
		DtoGallerist dtoGallerist = new DtoGallerist();
		DtoCar dtoCar = new DtoCar();
		DtoAddress dtoAddress = new DtoAddress();
		DtoAccount dtoAccount = new DtoAccount();

		BeanUtils.copyProperties(saledCar, dtoSaledCar);
		BeanUtils.copyProperties(saledCar.getCustomer(), dtoCustomer);
		BeanUtils.copyProperties(saledCar.getGallerist(), dtoGallerist);
		BeanUtils.copyProperties(saledCar.getCar(), dtoCar);
		BeanUtils.copyProperties(saledCar.getCustomer().getAddress(), dtoAddress);
		BeanUtils.copyProperties(saledCar.getCustomer().getAccount(), dtoAccount);

		// customer
		dtoCustomer.setDtoAddress(dtoAddress);
		dtoCustomer.setDtoAccount(dtoAccount);
		dtoSaledCar.setCreateTime(new Date());
		dtoSaledCar.setCustomer(dtoCustomer);

		// gallerist
		dtoGallerist.setCreateTime(new Date());
		dtoGallerist.setAddress(dtoAddress);
		dtoSaledCar.setGallerist(dtoGallerist);

		// car
		dtoCar.setCreateTime(new Date());
		dtoSaledCar.setCar(dtoCar);

		return dtoSaledCar;

	}
}