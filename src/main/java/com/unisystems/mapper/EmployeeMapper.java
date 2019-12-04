package com.unisystems.mapper;

import com.unisystems.model.*;
import com.unisystems.repository.EmployeeRepository;
import com.unisystems.response.EmployeeResponse;
import com.unisystems.response.generic.Error;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllEmployeeResponse;
import com.unisystems.strategy.employeeStrategy.SearchEmployeeStrategy;
import com.unisystems.strategy.employeeStrategy.SearchEmployeeStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EmployeeMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SearchEmployeeStrategyFactory searchEmployeeStrategyFactory;

    public EmployeeResponse mapEmployeeResponseFromEmployee(Employee emp) {
        EmployeeResponse employeeResponse = new EmployeeResponse(
                emp.getEmployeeId(),
                emp.getRegistrationNumber(),
                getEmployeeFullName(emp),
                emp.getPhoneNumber(),
                findWorkingPeriod(emp.getRecruitmentDate(), emp.getReleaseDate()),
                emp.getEmployeeStatus(),
                emp.getContractType(),
                findUnit(emp),
                emp.getPosition()
        );
        return employeeResponse;
    }

    public String findUnit(Employee emp) {
        Unit empUnit = emp.getEmployeeUnitRef();
        return empUnit == null ? "N/A" : empUnit.getUnitName();
    }

    public String getEmployeeFullName(Employee emp) {
        return emp.getFirstName() + " " + emp.getLastName();
    }

    public String findWorkingPeriod(Date startDate, Date endDate) {
        if (endDate == null) return "PRESENT";
        LocalDate endDateLocal = endDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate startDateLocal = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        Period diff = Period.between(startDateLocal, endDateLocal);
        String response = "Years: " + diff.getYears() +
                ", Months: " + diff.getMonths() +
                ", Days " + diff.getDays();
        return response;
    }

    public List<EmployeeResponse> mapAllEmployees(List<Employee> employees) {
        List<EmployeeResponse> employeeResponses = new ArrayList<EmployeeResponse>();
        for(Employee e: employees){
            employeeResponses.add(mapEmployeeResponseFromEmployee(e));
        }
        return employeeResponses;
    }

    public GenericResponse<GetAllEmployeeResponse> getResponseFromStrategy(String searchCriteria,
                   GenericResponse<GetAllEmployeeResponse> genericResponse, List<Error> errors, String criteriaId) {

        List<Employee> retrievedEmployees = (List<Employee>) employeeRepository.findAll();
        List<EmployeeResponse> employeesResponse = new ArrayList<EmployeeResponse>();
        //Strategy design pattern
        SearchEmployeeStrategy strategy = searchEmployeeStrategyFactory.makeStrategyForCriteria(searchCriteria);
        //If strategy is null, return the following error
        if (strategy == null) {
            Error error = new Error(100,
                    "SearchCriteria incorrect",
                    "It should be one of the following: [unit,department,businessUnit,company]");
            errors.add(error);
            genericResponse.setErrors(errors);
            return genericResponse;
        }
        //If strategy is OK
        employeesResponse = mapAllEmployees(strategy.execute(Long.parseLong(criteriaId), retrievedEmployees));
        if(employeesResponse.size() == 0) {
            Error error = new Error(1009,
                    "CriteriaId N/A",
                    "No such"+searchCriteria+" with this criteriaId" +
                            "or this "+searchCriteria+" does not contain any employees");
            errors.add(error);
            genericResponse.setErrors(errors);
        }
        //Set response
        if(genericResponse.getErrors() == null)
            genericResponse.setData(new GetAllEmployeeResponse(employeesResponse));

        return genericResponse;
    }
}