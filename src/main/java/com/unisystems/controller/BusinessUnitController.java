package com.unisystems.controller;

import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllBusinessUnitResponse;
import com.unisystems.service.BusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BusinessUnitController {
    @Autowired
    BusinessUnitService businessUnitService;

    @GetMapping("/getBusinessUnits")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getBusinessUnits() {
        GenericResponse<GetAllBusinessUnitResponse> finalResponse = businessUnitService.getBusinessUnits();
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }
}
