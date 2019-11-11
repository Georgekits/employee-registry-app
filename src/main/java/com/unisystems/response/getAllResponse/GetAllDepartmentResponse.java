package com.unisystems.response.getAllResponse;

import com.unisystems.response.DepartmentResponse;

import java.util.List;

public class GetAllDepartmentResponse {
    private List<DepartmentResponse> departmentResponseList;

    public GetAllDepartmentResponse(List<DepartmentResponse> departmentResponseList) {
        this.departmentResponseList = departmentResponseList;
    }

    public List<DepartmentResponse> getDepartmentResponseList() {
        return departmentResponseList;
    }

    public void setDepartmentResponseList(List<DepartmentResponse> departmentResponseList) {
        this.departmentResponseList = departmentResponseList;
    }
}
