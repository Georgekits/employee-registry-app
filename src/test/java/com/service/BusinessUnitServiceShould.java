package com.service;

import com.unisystems.mapper.BusinessUnitMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import com.unisystems.repository.BusinessUnitRepository;
import com.unisystems.response.BusinessUnitResponse;
import com.unisystems.service.BusinessUnitService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BusinessUnitServiceShould {

    private BusinessUnitService businessUnitService;

    BusinessUnitResponse businessUnitResponseFromMapper;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private BusinessUnitMapper businessUnitMapper;

    Company firstCompany = new Company("UniSystems","That's the first company of this project and it listens to UniSystems");


    private List<BusinessUnit> mockedBusinessUnits = new ArrayList<BusinessUnit>() {
        {
            add(new BusinessUnit("Public Sector",
                    "That's the business unit that is well known as Public Sector",firstCompany));
            add(new BusinessUnit("International Sector",
                    "That's the business unit that is well known as International Sector",firstCompany ));
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(businessUnitRepository.findAll()).thenReturn(mockedBusinessUnits);
        businessUnitResponseFromMapper = new BusinessUnitResponse(1L, "Public Sector",
                "That's the business unit that is well known as Public Sector", firstCompany.getCompanyName());
        when(businessUnitMapper.mapBUResponseFromBU(any())).thenReturn(businessUnitResponseFromMapper);
        businessUnitService = new BusinessUnitService(businessUnitRepository,businessUnitMapper);

    }

    @Test
    public void retrievedBusinessUnitsFromRepository() {
        businessUnitService.getBusinessUnits();
        Mockito.verify(businessUnitRepository).findAll();
    }

    @Test
    public void usesBusinessUnitMapper() {
        businessUnitService.getBusinessUnits();
        Mockito.verify(businessUnitMapper, times(2)).mapBUResponseFromBU(any());
    }


}
