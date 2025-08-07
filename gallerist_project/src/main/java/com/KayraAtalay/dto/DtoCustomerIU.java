package com.KayraAtalay.dto;

import java.util.Date;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCustomerIU {

    @NotEmpty(message = "First name cannot be empty")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "First name must contain only letters")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Pattern(regexp = "^[a-zA-ZçÇğĞıİöÖşŞüÜ]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotEmpty(message = "TCKN cannot be empty")
    @Size(min = 11, max = 11, message = "TCKN must be exactly 11 digits")
    @Pattern(regexp = "^[0-9]+$", message = "TCKN must contain only digits")
    private String tckn;

    @NotNull(message = "Birth date cannot be null")
    private Date birthOfDate;

    @NotNull(message = "Address ID cannot be null")
    private Long addressId;

    @NotNull(message = "Account ID cannot be null")
    private Long accountId;

}