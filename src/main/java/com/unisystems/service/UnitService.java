package com.unisystems.service;

import com.unisystems.mapper.UnitMapper;
import com.unisystems.model.Unit;
import com.unisystems.repository.UnitRepository;
import com.unisystems.response.UnitResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Autowired
    UnitMapper unitMapper;

    public GenericResponse<GetAllUnitResponse> getAllUnits() {
        List<Unit> retrievedUnits = (List<Unit>) unitRepository.findAll();
        List<UnitResponse> units = new ArrayList<UnitResponse>();
        GenericResponse<GetAllUnitResponse> response = new GenericResponse<GetAllUnitResponse>();

        retrievedUnits.forEach((unit) -> {
            units.add(unitMapper.mapDepartmentResponseFromDepartment(unit));
        });
        response.setData(new GetAllUnitResponse(units));

        return response;
    }
}
