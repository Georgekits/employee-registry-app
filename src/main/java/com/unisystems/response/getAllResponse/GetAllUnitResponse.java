package com.unisystems.response.getAllResponse;

import com.unisystems.response.UnitResponse;

import java.util.List;

public class GetAllUnitResponse {
    private List<UnitResponse> unitResponseList;

    public GetAllUnitResponse(List<UnitResponse> unitResponseList) {
        this.unitResponseList = unitResponseList;
    }

    public List<UnitResponse> getUnitResponseList() {
        return unitResponseList;
    }

    public void setUnitResponseList(List<UnitResponse> unitResponseList) {
        this.unitResponseList = unitResponseList;
    }
}
