package com.KayraAtalay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristIU;
import com.KayraAtalay.model.Gallerist;

public interface IGalleristService {
	
	public DtoGallerist saveGallerist(DtoGalleristIU dtoGalleristIU);
	
	public Page<Gallerist> findAllPageable(Pageable pageable);
	
	public Page<DtoGallerist> findAllPageableDto(Pageable pageable);
	
	public DtoGallerist findGalleristById(Long id);
	
	public DtoAddress updateGalleristAddress(Long galleristId, DtoAddressIU dtoAddressIU);
}
