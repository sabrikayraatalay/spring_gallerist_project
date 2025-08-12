package com.KayraAtalay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.SaledCar;

@Repository
public interface SaledCarRepository extends JpaRepository<SaledCar, Long> {
	
	List<SaledCar>  findByGallerist(Gallerist gallerist);

}
