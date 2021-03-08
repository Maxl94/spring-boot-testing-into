package com.ausytechnologies.testingintro.persistence.repository;

import com.ausytechnologies.testingintro.persistence.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
