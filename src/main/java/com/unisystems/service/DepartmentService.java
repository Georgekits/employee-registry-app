package com.unisystems.service;

import com.unisystems.mapper.DepartmentMapper;
import com.unisystems.model.Department;
import com.unisystems.repository.DepartmentRepository;
import com.unisystems.response.DepartmentResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllDepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    public GenericResponse<GetAllDepartmentResponse> getAllDepartments() {
        List<Department> retrievedDepartments = (List<Department>) departmentRepository.findAll();
        List<DepartmentResponse> departments = new ArrayList<DepartmentResponse>();
        GenericResponse<GetAllDepartmentResponse> genericResponse = new GenericResponse<>();

        retrievedDepartments.forEach((department) -> {
            departments.add(departmentMapper.mapDepartmentResponseFromDepartment(department));
        });
        genericResponse.setData(new GetAllDepartmentResponse(departments));
        return genericResponse;
    }
}
