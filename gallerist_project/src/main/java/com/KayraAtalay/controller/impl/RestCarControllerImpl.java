package com.KayraAtalay.controller.impl;

import java.math.BigDecimal;
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

import com.KayraAtalay.controller.IRestCarController;
import com.KayraAtalay.controller.RestBaseController;
import com.KayraAtalay.controller.RootEntity;
import com.KayraAtalay.dto.DtoCar;
import com.KayraAtalay.dto.DtoCarIU;
import com.KayraAtalay.service.ICarService;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/car")
public class RestCarControllerImpl extends RestBaseController implements IRestCarController {

	@Autowired
	private ICarService carService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarIU dtoCarIU) {
		return ok(carService.saveCar(dtoCarIU));
	}

	@GetMapping("/list/pageable")
	@Override
	public RootEntity<RestPageableEntity<DtoCar>> findAllPageable(PageableRequest pageableRequest) {

		Page<DtoCar> page = carService.findAllPageableDto(toPageable(pageableRequest));

		RestPageableEntity<DtoCar> pageableResponse = toPageableResponse(page, page.getContent());
		return ok(pageableResponse);
	}

	@GetMapping("/{id}")
	@Override
	public RootEntity<DtoCar> findCarById(@PathVariable Long id) {
		return ok(carService.findCarById(id));
	}

	@GetMapping("/list")
	@Override
	public RootEntity<List<DtoCar>> findCarByBrand(@RequestParam String brand) {
		return ok(carService.findCarByBrand(brand));
	}

	@PutMapping("/update/price/{id}")
	@Override
	public RootEntity<DtoCar> updateCarPrice(@PathVariable Long id, @RequestParam BigDecimal price) {
		return ok(carService.updateCarPrice(id, price));
	}

}
