package com.unisystems.service;

import com.unisystems.mapper.EmployeeMapper;
import com.unisystems.model.Employee;
import com.unisystems.repository.EmployeeRepository;
import com.unisystems.response.EmployeeResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    Utils utils;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
                           Utils utils){
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.utils = utils;
    }

    public GenericResponse<GetAllEmployeeResponse> getAllEmployees() {
        List<Employee> retrievedEmployees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeResponse> employees = new ArrayList<EmployeeResponse>();
        GenericResponse<GetAllEmployeeResponse> genericResponse = new GenericResponse<>();

        retrievedEmployees.forEach((employee) -> {
            employees.add(employeeMapper.mapEmployeeResponseFromEmployee(employee));
        });
        genericResponse.setData(new GetAllEmployeeResponse(employees));
        return genericResponse;
    }

    public GenericResponse<GetAllEmployeeResponse> getEmployeesWithCriteria(String searchCriteria, String criteriaId) {
        GenericResponse<GetAllEmployeeResponse> genericResponse = new GenericResponse<>();
        List<Error> errors = new ArrayList<>();
        //Check if criteriaId is numeric
        if(!utils.isNumeric(criteriaId)){
            Error error = new Error(100,
                    "EmployeeId numeric only",
                    "The employeeId, must only be numeric");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }

        return employeeMapper.getResponseFromStrategy(searchCriteria, genericResponse, errors, criteriaId);
    }
}