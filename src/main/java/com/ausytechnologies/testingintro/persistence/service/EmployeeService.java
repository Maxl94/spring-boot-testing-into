package com.ausytechnologies.testingintro.persistence.service;

import java.util.Optional;

import com.ausytechnologies.testingintro.web.model.EmployeeRepr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Optional<EmployeeRepr> findById(Long id);

    Page<EmployeeRepr> findAll(Pageable pageable);
    
    EmployeeRepr create(EmployeeRepr employeeRepr);
}
