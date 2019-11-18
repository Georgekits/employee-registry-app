package com.unisystems.strategy.employeeStrategy;

import org.springframework.stereotype.Component;

@Component
public class SearchEmployeeStrategyFactory {
    public SearchEmployeeStrategy makeStrategyForCriteria(String criteria){
        switch (criteria.toLowerCase()){
            case "unit":
                return new SearchEmployeeByUnitStrategy();
            case "department":
                return new SearchEmployeeByDepartmentStrategy();
            case "businessUnit":
                return new SearchEmployeeByBUStrategy();
            case "company":
                return new SearchEmployeeByCompanyStrategy();
            default:
                return null;
        }
    }
}