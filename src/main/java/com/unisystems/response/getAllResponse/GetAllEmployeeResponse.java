package com.unisystems.response.getAllResponse;

import com.unisystems.response.EmployeeResponse;

import java.util.List;

public class GetAllEmployeeResponse {
    private List<EmployeeResponse> employeeResponseList;

    public GetAllEmployeeResponse(List<EmployeeResponse> employeeResponseList) {
        this.employeeResponseList = employeeResponseList;
    }

    public List<EmployeeResponse> getEmployeeResponseList() {
        return employeeResponseList;
    }

    public void setEmployeeResponseList(List<EmployeeResponse> employeeResponseList) {
        this.employeeResponseList = employeeResponseList;
    }
}
