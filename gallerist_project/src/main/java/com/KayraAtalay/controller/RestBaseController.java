package com.KayraAtalay.controller;

public class RestBaseController {

	
	
	public <T> RootEntity<T> ok(T payload) {
		return RootEntity.ok(payload);
	}

}
