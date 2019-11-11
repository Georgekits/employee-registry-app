package com.unisystems.mapper;

import com.unisystems.model.Unit;
import com.unisystems.response.UnitResponse;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {
    public UnitResponse mapDepartmentResponseFromDepartment(Unit unit) {
        UnitResponse unitResponse = new UnitResponse (
            unit.getUnitId(),
            unit.getUnitName(),
            unit.getUnitDescription(),
            unit.getDepartmentRef().getDepartmentName()
        );
        return  unitResponse;
    }
}
