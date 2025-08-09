package com.KayraAtalay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	 List<Car> findByBrand(String brand);

}
