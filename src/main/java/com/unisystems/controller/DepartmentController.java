package com.unisystems.controller;

import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllDepartmentResponse;
import com.unisystems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    public DepartmentController(DepartmentService service){this.departmentService=service;}

    @GetMapping("/getDepartments")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getDepartments() {
        GenericResponse<GetAllDepartmentResponse> finalResponse = departmentService.getAllDepartments();
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }
}
