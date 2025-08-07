package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestCustomerController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoCustomer;
import com.KayraAtalay.dto.DtoCustomerIU;
import com.KayraAtalay.model.Customer;
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
		
		Page<Customer> page = customerService.findAllPageable(toPageable(pageableRequest));
		
		RestPageableEntity<DtoCustomer> pageableResponse = toPageableResponse(page, customerService.toDtoList(page.getContent()));
		return ok(pageableResponse);
	}

}
