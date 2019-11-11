package com.unisystems.response.strategy;

import com.unisystems.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeeByUnitStrategy implements SearchEmployeeStrategy {
    @Override
    public List<Employee> execute(Long criteriaId, List<Employee> allEmployees) {
        List<Employee> employees = new ArrayList<Employee>();
        for(Employee employee: allEmployees){
            if(employee.getEmployeeUnitRef().getUnitId() == criteriaId){
                employees.add(employee);
            }
        }
        return employees;
    }
}