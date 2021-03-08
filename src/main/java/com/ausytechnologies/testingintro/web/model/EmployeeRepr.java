package com.ausytechnologies.testingintro.web.model;

import java.util.Objects;

import com.ausytechnologies.testingintro.persistence.model.util.Status;
import org.springframework.hateoas.RepresentationModel;

public class EmployeeRepr extends RepresentationModel<EmployeeRepr> {
    private Long employeeId;

    private String firstname;

    private String lastname;

    private Status status;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        EmployeeRepr that = (EmployeeRepr) o;
        return Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), employeeId);
    }

    @Override
    public String toString() {
        return "EmployeeRepr{" +
                "employeeId=" + employeeId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", status=" + status +
                '}';
    }
}
