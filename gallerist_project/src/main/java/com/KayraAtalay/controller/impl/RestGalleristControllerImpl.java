package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestGalleristController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristIU;
import com.KayraAtalay.service.IGalleristService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/gallerist")
public class RestGalleristControllerImpl extends RestBaseController implements IRestGalleristController {
	
	@Autowired
	private IGalleristService galleristService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoGallerist> saveGallerist(@Valid @RequestBody DtoGalleristIU dtoGalleristIU) {
		return ok(galleristService.saveGallerist(dtoGalleristIU));
	}

	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoGallerist>> findAllPageable(PageableRequest request) {
		Page<DtoGallerist> page = galleristService.findAllPageableDto(toPageable(request));
		return ok(toPageableResponse(page, page.getContent()));
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoGallerist> findGalleristById(@PathVariable Long id) {
		return ok(galleristService.findGalleristById(id));
	}

	@PutMapping("/update/address/{id}")
	@Override
	public RootEntity<DtoAddress> updateGalleristAddress(@PathVariable("id") Long galleristId, @RequestBody DtoAddressIU dtoAddressIU) {
		return ok(galleristService.updateGalleristAddress(galleristId, dtoAddressIU));
	}
}