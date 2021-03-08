package com.ausytechnologies.testingintro.persistence.mapper;

import com.ausytechnologies.testingintro.web.model.EmployeeRepr;
import com.ausytechnologies.testingintro.persistence.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee employeeReprToEmployee(EmployeeRepr employeeRepr);

    EmployeeRepr employeeToEmployeeRepr(Employee employee);
}
