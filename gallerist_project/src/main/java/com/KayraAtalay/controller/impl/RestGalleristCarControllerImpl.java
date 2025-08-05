package com.KayraAtalay.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KayraAtalay.controller.IRestGalleristCarController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoGalleristCar;
import com.KayraAtalay.dto.DtoGalleristCarIU;
import com.KayraAtalay.service.IGalleristCarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("rest/api/gallerist-car")
public class RestGalleristCarControllerImpl extends RestBaseController implements IRestGalleristCarController {
	
	@Autowired
	private IGalleristCarService galleristCarService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoGalleristCar> saveGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU) {
				return ok(galleristCarService.saveGalleristCar(dtoGalleristCarIU));
	}

}
