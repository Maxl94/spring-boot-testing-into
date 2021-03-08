package com.ausytechnologies.testingintro.persistence.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ausytechnologies.testingintro.web.model.EmployeeRepr;
import com.ausytechnologies.testingintro.persistence.exception.PersistenceException;
import com.ausytechnologies.testingintro.persistence.mapper.EmployeeMapper;
import com.ausytechnologies.testingintro.persistence.model.Employee;
import com.ausytechnologies.testingintro.persistence.repository.EmployeeRepository;
import com.ausytechnologies.testingintro.persistence.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<EmployeeRepr> findById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.map(EmployeeMapper.INSTANCE::employeeToEmployeeRepr);
    }

    @Override
    public Page<EmployeeRepr> findAll(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        logger.debug("Found {} employees", employeePage.getNumberOfElements());

        /* Mapping */
        logger.debug("Mapping employees to employeeReprs");
        List<EmployeeRepr> employeeReprs = new ArrayList<>();
        for (Employee employee : employeePage.getContent()) {
            EmployeeRepr employeeRepr = EmployeeMapper.INSTANCE.employeeToEmployeeRepr(employee);
            employeeReprs.add(employeeRepr);
        }

        logger.debug("Creating page");
        return new PageImpl<>(employeeReprs, pageable, employeePage.getTotalElements());
    }

    @Override
    public EmployeeRepr create(EmployeeRepr employeeRepr) {
        try {
            Employee employee = EmployeeMapper.INSTANCE.employeeReprToEmployee(employeeRepr);
            employee = employeeRepository.saveAndFlush(employee);
            logger.debug("Created new employee. employeeId={}", employee.getEmployeeId());
            return EmployeeMapper.INSTANCE.employeeToEmployeeRepr(employee);
        } catch (Exception e) {
            logger.error("Could not create employee.", e);
            throw new PersistenceException("Could not create employee", e);
        }
    }
}
