package com.ausytechnologies.testingintro.web.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Optional;

import com.ausytechnologies.testingintro.web.model.EmployeeRepr;
import com.ausytechnologies.testingintro.persistence.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/employee")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class EmployeeController {
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    private final PagedResourcesAssembler<EmployeeRepr> pagedResourcesAssembler;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PagedResourcesAssembler<EmployeeRepr> pagedResourcesAssembler) {
        this.employeeService = employeeService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(value = "/{employeeId}", produces = {"application/hal+json"})
    public ResponseEntity<Object> findById(@PathVariable("employeeId") Long employeeId) {
        if (employeeId == null) {
            logger.error("The employeeId must not be null.");
            return new ResponseEntity<>("The employeeId must not be null.", HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<EmployeeRepr> employeeReprOptional = employeeService.findById(employeeId);
            if (employeeReprOptional.isPresent()) {
                EmployeeRepr employeeRepr = employeeReprOptional.get();
                employeeRepr.add(getLink(employeeRepr));
                return new ResponseEntity<>(employeeReprOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String msg = "Could not all employee. employeeId=" + employeeId;
            logger.error(msg, e);
            return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<EntityModel<EmployeeRepr>>> findAll(Pageable pageable) {
        try {
            Page<EmployeeRepr> employeeReprs = employeeService.findAll(pageable);

            for (EmployeeRepr employeeRepr : employeeReprs.getContent()) {
                logger.debug("Creating link representatives.");
                employeeRepr.add(getLink(employeeRepr));
            }

            Link link = linkTo(EmployeeController.class).withSelfRel();
            return new ResponseEntity<>(pagedResourcesAssembler.toModel(employeeReprs, link), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Could not find all employees", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = {"application/hal+json"})
    public ResponseEntity<EmployeeRepr> create(@Validated @RequestBody EmployeeRepr employeeRepr) {
        try {
            EmployeeRepr employeeReprCreated = employeeService.create(employeeRepr);
            employeeReprCreated.add(getLink(employeeRepr));
            return new ResponseEntity<>(employeeReprCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Could not create", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Link getLink(EmployeeRepr employeeRepr) {
        return linkTo(EmployeeController.class).slash(employeeRepr.getEmployeeId()).withSelfRel();
    }

}
