package com.KayraAtalay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.KayraAtalay.model.Gallerist;
import com.KayraAtalay.model.GalleristCar;

@Repository
public interface GalleristCarRepository extends JpaRepository<GalleristCar, Long> {

    Optional<GalleristCar> findByGalleristIdAndCarId(Long galleristId, Long carId);
    
    List<GalleristCar> findByGallerist(Gallerist gallerist);
    
  
	
}
