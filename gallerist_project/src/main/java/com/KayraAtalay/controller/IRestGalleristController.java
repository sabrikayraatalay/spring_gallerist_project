package com.KayraAtalay.controller;

import com.KayraAtalay.dto.DtoGallerist;
import com.KayraAtalay.dto.DtoGalleristIU;

public interface IRestGalleristController {
	
	public RootEntity<DtoGallerist> saveGallerist(DtoGalleristIU dtoGalleristIU);

}
