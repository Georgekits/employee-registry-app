package com.unisystems.controller;

import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllUnitResponse;
import com.unisystems.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnitController {
    @Autowired
    UnitService unitService;

    public UnitController(UnitService service){
        this.unitService = service;
    }

    @GetMapping("/getUnits")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity getAllUnits() {
        GenericResponse<GetAllUnitResponse> finalResponse = unitService.getAllUnits();
        if(finalResponse.getErrors() != null)
            return new ResponseEntity(finalResponse.getErrors(),
                    null,
                    HttpStatus.BAD_REQUEST);
        return new ResponseEntity(finalResponse.getData(),
                null,
                HttpStatus.OK);
    }
}
