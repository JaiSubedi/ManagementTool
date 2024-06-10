package com.test.toolmanagement.service;

import com.test.toolmanagement.entity.RentalAgreement;
import com.test.toolmanagement.entity.Tool;
import com.test.toolmanagement.exception.ToolNotFoundException;
import com.test.toolmanagement.exception.ValidationException;
import com.test.toolmanagement.repository.RentalAgreementRepository;
import com.test.toolmanagement.repository.ToolRepository;
import com.test.toolmanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final ToolRepository toolRepository;
    private final RentalAgreementRepository rentalAgreementRepository;

    public RentalAgreement createRentalAgreement(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) throws ToolNotFoundException,ValidationException {
        if (rentalDays < 1) {
            throw new ValidationException("Rental day count must be at least 1.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new ValidationException("Discount percent must be between 0 and 100.");
        }

        Tool tool = toolRepository.findByCode(toolCode);
        if (tool == null) {
            throw new ToolNotFoundException("Tool not found.");
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = DateUtils.calculateChargeDays(checkoutDate, dueDate, tool);

        BigDecimal dailyRentalCharge = tool.getDailyCharge().setScale(2, RoundingMode.HALF_UP);
        BigDecimal preDiscountCharge = dailyRentalCharge.multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

        RentalAgreement rentalAgreement = RentalAgreement.builder()
                .toolCode(tool.getCode())
                .toolType(tool.getType())
                .toolBrand(tool.getBrand())
                .rentalDays(rentalDays)
                .checkoutDate(checkoutDate)
                .dueDate(dueDate)
                .dailyRentalCharge(dailyRentalCharge)
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(discountPercent)
                .discountAmount(discountAmount)
                .finalCharge(finalCharge)
                .build();

        System.out.println(rentalAgreement.toString());

        return rentalAgreementRepository.save(rentalAgreement);
    }
}
