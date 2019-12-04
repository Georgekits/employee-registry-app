package com.unisystems.strategy.employeeStrategy;

import com.unisystems.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeeByCompanyStrategy implements SearchEmployeeStrategy {
    @Override
    public List<Employee> execute(Long criteriaId, List<Employee> allEmployees) {
        List<Employee> employees = new ArrayList<Employee>();
        for(Employee employee: allEmployees){
            if(employee.getEmployeeUnitRef().getDepartmentRef().getBusinessUnitRef().getCompanyRef().getCompanyId() == criteriaId)
                employees.add(employee);
        }
        return employees;
    }
}
