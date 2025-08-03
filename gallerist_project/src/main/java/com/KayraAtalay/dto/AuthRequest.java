package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	
	@NotEmpty
	private String username;
	
	@NotEmpty
	@Size(min = 8, max = 50)
	private String password;

}
