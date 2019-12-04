package com.unisystems.mapper;

import com.unisystems.model.BusinessUnit;
import com.unisystems.response.BusinessUnitResponse;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitMapper {
    public BusinessUnitResponse mapBUResponseFromBU(BusinessUnit bu) {
        BusinessUnitResponse businessUnitResponse = new BusinessUnitResponse (
                bu.getBusinessUnitId(),
                bu.getBusinessUnitName(),
                bu.getBusinessUnitDescription(),
                bu.getCompanyRef().getCompanyName()
        );

        return businessUnitResponse;
    }
}
