package com.KayraAtalay.dto;

import java.math.BigDecimal;

import com.KayraAtalay.enums.CurrencyType;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCarIU {

    @NotBlank(message = "License plate cannot be empty")
    @Pattern(regexp = "^(0[1-9]|[1-7][0-9]|8[0-1])[\\s-]?([A-Z]{1,3})[\\s-]?([0-9]{1,4})$", 
             message = "Invalid Turkish license plate format. Example formats: 34 ABC 123, 34-ABC-123, 34ABC123")
    private String licensePlate;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    @NotBlank(message = "Model cannot be empty")
    private String model;

    @NotNull(message = "Production year cannot be null")
    @Digits(integer = 4, fraction = 0, message = "Production year must be a 4-digit number")
    @Min(value = 1900, message = "Production year must be after 1900")
    private Integer productionYear;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Currency type cannot be null")
    private CurrencyType currencyType;

    @NotNull(message = "Damage price cannot be null")
    @Min(value = 0, message = "Damage price cannot be negative")
    private BigDecimal damagePrice;
}