package com.KayraAtalay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoGalleristIU {
	
	@NotBlank(message = "First name cannot be empty or just spaces")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "First name must contain only letters")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be empty or just spaces")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "Last name must contain only letters")
	private String lastName;
	
	@NotNull(message = "Address ID cannot be null")
	private Long addressId;

}