package com.unisystems.service;

import com.unisystems.mapper.BusinessUnitMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.repository.BusinessUnitRepository;
import com.unisystems.response.BusinessUnitResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllBusinessUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessUnitService {
    @Autowired
    BusinessUnitRepository businessUnitRepository;

    @Autowired
    BusinessUnitMapper businessUnitMapper;

    public BusinessUnitService(BusinessUnitRepository businessUnitRepository, BusinessUnitMapper businessUnitMapper) {
        this.businessUnitRepository = businessUnitRepository;
        this.businessUnitMapper = businessUnitMapper;
    }

    public GenericResponse<GetAllBusinessUnitResponse> getBusinessUnits() {
        List<BusinessUnit> retrievedBusinessUnits = (List<BusinessUnit>) businessUnitRepository.findAll();
        List<BusinessUnitResponse> businessUnits = new ArrayList<BusinessUnitResponse>();
        GenericResponse<GetAllBusinessUnitResponse> genericResponse = new GenericResponse<>();

        retrievedBusinessUnits.forEach((company) -> {
            businessUnits.add(businessUnitMapper.mapBUResponseFromBU(company));
        });
        genericResponse.setData(new GetAllBusinessUnitResponse(businessUnits));
        return genericResponse;
    }
}
