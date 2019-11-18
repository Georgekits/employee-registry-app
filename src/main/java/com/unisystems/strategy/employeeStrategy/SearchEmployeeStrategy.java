package com.unisystems.strategy.employeeStrategy;

import com.unisystems.model.Employee;
import java.util.List;

public interface SearchEmployeeStrategy {
    List<Employee> execute (Long criteriaId, List<Employee> allEmployees);
}