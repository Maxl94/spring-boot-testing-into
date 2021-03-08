package com.ausytechnologies.testingintro.persistence.mapper;

import com.ausytechnologies.testingintro.web.model.EmployeeRepr;
import com.ausytechnologies.testingintro.persistence.model.Employee;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-08T16:43:27+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.8.2.jar, environment: Java 1.8.0_241 (Oracle Corporation)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee employeeReprToEmployee(EmployeeRepr employeeRepr) {
        if ( employeeRepr == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmployeeId( employeeRepr.getEmployeeId() );
        employee.setFirstname( employeeRepr.getFirstname() );
        employee.setLastname( employeeRepr.getLastname() );
        employee.setStatus( employeeRepr.getStatus() );

        return employee;
    }

    @Override
    public EmployeeRepr employeeToEmployeeRepr(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeRepr employeeRepr = new EmployeeRepr();

        employeeRepr.setEmployeeId( employee.getEmployeeId() );
        employeeRepr.setFirstname( employee.getFirstname() );
        employeeRepr.setLastname( employee.getLastname() );
        employeeRepr.setStatus( employee.getStatus() );

        return employeeRepr;
    }
}
