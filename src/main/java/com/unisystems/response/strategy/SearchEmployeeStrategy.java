package com.unisystems.response.strategy;

import com.unisystems.model.Employee;
import java.util.List;

public interface SearchEmployeeStrategy {
    List<Employee> execute (Long criteriaId, List<Employee> allEmployees);
}