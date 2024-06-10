package com.test.toolmanagement.controller.web;

import com.test.toolmanagement.entity.RentalAgreement;
import com.test.toolmanagement.exception.ToolNotFoundException;
import com.test.toolmanagement.exception.ValidationException;
import com.test.toolmanagement.service.RentalService;
import com.test.toolmanagement.service.ToolService;
import com.test.toolmanagement.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rental")
public class RentalWebController {

    private final RentalService rentalService;
    private final ToolService toolService;

    @GetMapping("/form")
    public String rentalForm(Model model) {
        model.addAttribute("tools",toolService.getAllTools());
        model.addAttribute("today", LocalDate.now());
        return "rental-form";
    }

    @PostMapping("/receipt")
    public String generateReceipt(
            @RequestParam String toolCode,
            @RequestParam int rentalDays,
            @RequestParam int discountPercent,
            @RequestParam String checkoutDate,
            Model model,
            RedirectAttributes redirectAttributes){

        try {
            LocalDate checkout = DateUtils.parseDate(checkoutDate);
            RentalAgreement rentalAgreement = rentalService.createRentalAgreement(toolCode, rentalDays, discountPercent, checkout);
            model.addAttribute("rentalAgreement", rentalAgreement);
            return "receipt";
        } catch (ValidationException | ToolNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/rental/form";
        }
    }
}
