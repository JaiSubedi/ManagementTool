package com.test.toolmanagement.entity;

import com.test.toolmanagement.utils.DateUtils;
import lombok.*;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    @Override
    public String toString() {
        return String.format("Tool code: %s%n" +
                        "Tool type: %s%n" +
                        "Tool brand: %s%n" +
                        "Rental days: %d%n" +
                        "Check out date: %s%n" +
                        "Due date: %s%n" +
                        "Daily rental charge: $%.2f%n" +
                        "Charge days: %d%n" +
                        "Pre-discount charge: $%.2f%n" +
                        "Discount percent: %d%%%n" +
                        "Discount amount: $%.2f%n" +
                        "Final charge: $%.2f%n",
                toolCode, toolType, toolBrand, rentalDays, DateUtils.formatDate(checkoutDate),
                DateUtils.formatDate(dueDate), dailyRentalCharge, chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }
}
