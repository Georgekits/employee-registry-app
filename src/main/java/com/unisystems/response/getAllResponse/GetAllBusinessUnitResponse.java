package com.unisystems.response.getAllResponse;

import com.unisystems.response.BusinessUnitResponse;

import java.util.List;

public class GetAllBusinessUnitResponse {
    private List<BusinessUnitResponse> businessUnitResponseList;

    public GetAllBusinessUnitResponse(List<BusinessUnitResponse> businessUnitResponseList) {
        this.businessUnitResponseList = businessUnitResponseList;
    }

    public List<BusinessUnitResponse> getBusinessUnitResponseList() {
        return businessUnitResponseList;
    }

    public void setBusinessUnitResponseList(List<BusinessUnitResponse> businessUnitResponseList) {
        this.businessUnitResponseList = businessUnitResponseList;
    }
}
