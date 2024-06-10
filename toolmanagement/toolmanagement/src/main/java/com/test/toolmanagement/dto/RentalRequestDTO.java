package com.test.toolmanagement.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {
    @NotBlank(message = "Tool code is required")
    private String toolCode;

    @Min(value = 1, message = "Rental day count must be at least 1")
    @NotNull(message = "Rental day count is required")
    private Integer rentalDays;

    @Min(value = 0, message = "Discount percent must be between 0 and 100")
    @Max(value = 100, message = "Discount percent must be between 0 and 100")
    @NotNull(message = "Discount percent is required")
    private Integer discountPercent;

    @NotNull(message = "Checkout date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkoutDate;
}
