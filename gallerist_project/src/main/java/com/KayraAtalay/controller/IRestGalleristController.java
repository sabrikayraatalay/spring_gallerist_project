package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoAddress;
import com.KayraAtalay.dto.DtoAddressIU;
import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristIU;
import com.KayraAtalay.utils.PageableRequest;
import com.KayraAtalay.utils.RestPageableEntity;

public interface IRestGalleristController {
	
	public RootEntity<DtoGallerist> saveGallerist(DtoGalleristIU dtoGalleristIU);
	
	public RootEntity<RestPageableEntity<DtoGallerist>> findAllPageable(PageableRequest request);
	
	public RootEntity<DtoGallerist> findGalleristById(Long id);
	
	public RootEntity<DtoAddress> updateGalleristAddress(Long galleristId, DtoAddressIU dtoAddressIU);

}
