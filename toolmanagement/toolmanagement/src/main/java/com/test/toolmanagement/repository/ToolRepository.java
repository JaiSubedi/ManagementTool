package com.test.toolmanagement.repository;

import com.test.toolmanagement.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByCode(String code);
}
