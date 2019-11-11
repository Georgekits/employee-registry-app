package com.unisystems.mapper;

import com.unisystems.model.Department;
import com.unisystems.response.DepartmentResponse;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {
    public DepartmentResponse mapDepartmentResponseFromDepartment(Department dep) {
        DepartmentResponse departmentResponse = new DepartmentResponse(
                dep.getDepartmentId(),
                dep.getDepartmentName(),
                dep.getDepartmentDescription(),
                dep.getBusinessUnitRef().getBusinessUnitName()
        );
        return departmentResponse;
    }
}
