package com.test.toolmanagement.controller;

import com.test.toolmanagement.dto.RentalRequestDTO;
import com.test.toolmanagement.entity.RentalAgreement;
import com.test.toolmanagement.exception.ToolNotFoundException;
import com.test.toolmanagement.exception.ValidationException;
import com.test.toolmanagement.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/checkout")
    public ResponseEntity<RentalAgreement> checkout(@Valid @RequestBody RentalRequestDTO rentalRequestDTO) throws ToolNotFoundException, ValidationException {
        return ResponseEntity.ok(rentalService.createRentalAgreement(
                rentalRequestDTO.getToolCode(),
                rentalRequestDTO.getRentalDays(),
                rentalRequestDTO.getDiscountPercent(),
                rentalRequestDTO.getCheckoutDate()
        ));
    }
}
