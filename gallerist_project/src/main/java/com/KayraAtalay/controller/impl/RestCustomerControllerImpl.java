package com.KayraAtalay.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCustomerController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoAccount;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.service.ICustomerService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements IRestCustomerController {

	@Autowired
	private ICustomerService customerService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerIU dtoCustomerIU) {
		return ok(customerService.saveCustomer(dtoCustomerIU));
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCustomer> findCustomerById(@PathVariable Long id) {
		return ok(customerService.findCustomerById(id));
	}
	
	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoCustomer>> findAllPageable(PageableRequest pageableRequest) {
		
		
		Page<DtoCustomer> page = customerService.findAllPageableDto(toPageable(pageableRequest));
		
		RestPageableEntity<DtoCustomer> pageableResponse = toPageableResponse(page, page.getContent());
		
		return ok(pageableResponse);
	}

	@GetMapping("/account/{id}")
	@Override
	public RootEntity<DtoAccount> findCustomerAccount(@PathVariable Long id) {
				return ok(customerService.findCustomerAccount(id));
	}

	@GetMapping("")
	@Override
	public RootEntity<List<DtoCustomer>> findCustomerByFirstName(@RequestParam String firstName) {
		return ok(customerService.findCustomerByFirstName(firstName.substring(0, 1).toUpperCase() + firstName.substring(1)));
	}

	@PutMapping("/update/address/{id}")
	@Override
	public RootEntity<DtoAddress> updateCustomerAddress(@PathVariable Long id, 
														@RequestBody DtoAddressIU dtoAddressIU) {
		
		return ok(customerService.updateCustomerAddress(id, dtoAddressIU));
	}

}