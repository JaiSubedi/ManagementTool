package com.test.toolmanagement.service;

import com.test.toolmanagement.entity.RentalAgreement;
import com.test.toolmanagement.entity.Tool;
import com.test.toolmanagement.exception.ToolNotFoundException;
import com.test.toolmanagement.exception.ValidationException;
import com.test.toolmanagement.repository.RentalAgreementRepository;
import com.test.toolmanagement.repository.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RentalServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @Mock
    private RentalAgreementRepository rentalAgreementRepository;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        when(toolRepository.findByCode("JAKR")).thenReturn(new Tool(null, "JAKR", "Jackhammer", "Ridgid", new BigDecimal("2.99"), true, false, false));
        when(toolRepository.findByCode("LADW")).thenReturn(new Tool(null, "LADW", "Ladder", "Werner", new BigDecimal("1.99"), true, true, false));
        when(toolRepository.findByCode("CHNS")).thenReturn(new Tool(null, "CHNS", "Chainsaw", "Stihl", new BigDecimal("1.49"), true, false, true));
        when(toolRepository.findByCode("JAKD")).thenReturn(new Tool(null, "JAKD", "Jackhammer", "DeWalt", new BigDecimal("2.99"), true, false, false));

        when(rentalAgreementRepository.save(any(RentalAgreement.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }


    @Test
    void testCreateRentalAgreement_JAKR_InvalidDiscount() {
        assertThrows(ValidationException.class, () -> {
            rentalService.createRentalAgreement("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
    }

    @Test
    void testCreateRentalAgreement_LADW_Valid() throws ToolNotFoundException, ValidationException {
        RentalAgreement rentalAgreement = rentalService.createRentalAgreement("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertEquals("LADW", rentalAgreement.getToolCode());
        assertEquals(LocalDate.of(2020, 7, 5), rentalAgreement.getDueDate());
        assertEquals(2, rentalAgreement.getChargeDays());
        assertEquals(new BigDecimal("3.98"), rentalAgreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("0.40"), rentalAgreement.getDiscountAmount());
        assertEquals(new BigDecimal("3.58"), rentalAgreement.getFinalCharge());
    }

    @Test
    void testCreateRentalAgreement_CHNS_Valid() throws ToolNotFoundException, ValidationException {
        RentalAgreement rentalAgreement = rentalService.createRentalAgreement("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        assertEquals("CHNS", rentalAgreement.getToolCode());
        assertEquals(LocalDate.of(2015, 7, 7), rentalAgreement.getDueDate());
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(new BigDecimal("4.47"), rentalAgreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("1.12"), rentalAgreement.getDiscountAmount());
        assertEquals(new BigDecimal("3.35"), rentalAgreement.getFinalCharge());
    }

    @Test
    void testCreateRentalAgreement_JAKD_Valid() throws ToolNotFoundException, ValidationException {
        RentalAgreement rentalAgreement = rentalService.createRentalAgreement("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        assertEquals("JAKD", rentalAgreement.getToolCode());
        assertEquals(LocalDate.of(2015, 9, 9), rentalAgreement.getDueDate());
        assertEquals(3, rentalAgreement.getChargeDays());
        assertEquals(new BigDecimal("8.97"), rentalAgreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("0.00"), rentalAgreement.getDiscountAmount());
        assertEquals(new BigDecimal("8.97"), rentalAgreement.getFinalCharge());
    }

    @Test
    void testCreateRentalAgreement_JAKR_Valid() throws ToolNotFoundException, ValidationException {
        RentalAgreement rentalAgreement = rentalService.createRentalAgreement("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        assertEquals("JAKR", rentalAgreement.getToolCode());
        assertEquals(LocalDate.of(2015, 7, 11), rentalAgreement.getDueDate());
        assertEquals(5, rentalAgreement.getChargeDays());
        assertEquals(new BigDecimal("14.95"), rentalAgreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("0.00"), rentalAgreement.getDiscountAmount());
        assertEquals(new BigDecimal("14.95"), rentalAgreement.getFinalCharge());
    }

    @Test
    void testCreateRentalAgreement_JAKR_ValidWithDiscount() throws ToolNotFoundException, ValidationException {
        RentalAgreement rentalAgreement = rentalService.createRentalAgreement("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        assertEquals("JAKR", rentalAgreement.getToolCode());
        assertEquals(LocalDate.of(2020, 7, 6), rentalAgreement.getDueDate());
        assertEquals(1, rentalAgreement.getChargeDays());
        assertEquals(new BigDecimal("2.99"), rentalAgreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("1.50"), rentalAgreement.getDiscountAmount());
        assertEquals(new BigDecimal("1.49"), rentalAgreement.getFinalCharge());
    }
}
