package com.unisystems.controller;

import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    public EmployeeController(EmployeeService service) {
        this.employeeService = service;
    }

    @GetMapping("/getEmployees")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getEmployees() {
        GenericResponse<GetAllEmployeeResponse> finalResponse = employeeService.getAllEmployees();
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }

    @GetMapping("/getEmployees/{searchCriteria}/{criteriaId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getEmployeeWithCriteria(@PathVariable String searchCriteria,
                                                          @PathVariable String criteriaId) {
        GenericResponse<GetAllEmployeeResponse> finalResponse = employeeService.getEmployeesWithCriteria(searchCriteria,criteriaId);
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }
}