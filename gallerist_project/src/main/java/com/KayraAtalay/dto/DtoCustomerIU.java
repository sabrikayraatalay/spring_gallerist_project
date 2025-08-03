package com.KayraAtalay.dto;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCustomerIU {
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	private String tckn;
	
	@NotNull
	private Date birthOfDate;
	
	@NotNull
	private Long addressId;
	
	@NotNull
	private Long accountId;


}
