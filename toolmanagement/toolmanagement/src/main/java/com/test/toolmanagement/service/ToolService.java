package com.test.toolmanagement.service;

import com.test.toolmanagement.entity.Tool;
import com.test.toolmanagement.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool getToolByCode(String code) {
        return toolRepository.findByCode(code);
    }

    public void save(List<Tool> tools) {
        toolRepository.saveAll(tools);
    }
}
