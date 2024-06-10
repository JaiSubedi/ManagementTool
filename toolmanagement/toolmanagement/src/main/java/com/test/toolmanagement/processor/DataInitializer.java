package com.test.toolmanagement.processor;

import com.test.toolmanagement.entity.Tool;
import com.test.toolmanagement.service.ToolService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private ToolService toolService;

    @PostConstruct
    public void init() {
        List<Tool> tools = loadTools();
        toolService.save(tools);
    }

    private List<Tool> loadTools() {
        List<Tool> tools = new ArrayList<>();

        tools.add(Tool.builder()
                .code("CHNS")
                .type("Chainsaw")
                .brand("Stihl")
                .dailyCharge(BigDecimal.valueOf(1.49))
                .weekdayCharge(true)
                .weekendCharge(false)
                .holidayCharge(true)
                .build());

        tools.add(Tool.builder()
                .code("LADW")
                .type("Ladder")
                .brand("Werner")
                .dailyCharge(BigDecimal.valueOf(1.99))
                .weekdayCharge(true)
                .weekendCharge(true)
                .holidayCharge(false)
                .build());

        tools.add(Tool.builder()
                .code("JAKD")
                .type("Jackhammer")
                .brand("DeWalt")
                .dailyCharge(BigDecimal.valueOf(2.99))
                .weekdayCharge(true)
                .weekendCharge(false)
                .holidayCharge(false)
                .build());

        tools.add(Tool.builder()
                .code("JAKR")
                .type("Jackhammer")
                .brand("Ridgid")
                .dailyCharge(BigDecimal.valueOf(2.99))
                .weekdayCharge(true)
                .weekendCharge(false)
                .holidayCharge(false)
                .build());

        return tools;
    }
}
