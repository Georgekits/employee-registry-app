package com.service;

import com.unisystems.mapper.DepartmentMapper;
import com.unisystems.model.BusinessUnit;
import com.unisystems.model.Company;
import com.unisystems.model.Department;
import com.unisystems.repository.DepartmentRepository;
import com.unisystems.response.DepartmentResponse;
import com.unisystems.response.generic.GenericResponse;
import com.unisystems.response.getAllResponse.GetAllDepartmentResponse;
import com.unisystems.service.DepartmentService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DepartmentServiceShould {
    @InjectMocks
    private DepartmentService departmentService;
    @Mock
    private DepartmentResponse departmentResponseFromMapper;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private DepartmentMapper departmentMapper;

    private Company companyInput = new Company("UniSystems", "That's the first company of this project and it listens to UniSystems");
    private BusinessUnit businessUnitInput = new BusinessUnit("PublicSector", "That's the business unit that is well known as Public Sector", companyInput);
    private ArrayList<Department> mockedDepartments = new ArrayList<Department>();


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockedDepartments.add(new Department("BusinessConsultants","That's the department that is well known as Business Consultants Dept",businessUnitInput));
        mockedDepartments.add(new Department("HR", "That's the department that is well known as HR",businessUnitInput));

        when(departmentRepository.findAll()).thenReturn(mockedDepartments);
        when(departmentMapper.mapDepartmentResponseFromDepartment(any())).thenReturn(departmentResponseFromMapper);

    }

    @Test
    public void retrieveDepartmentsFromRepository(){
        departmentService.getAllDepartments();
        Mockito.verify(departmentRepository).findAll();
    }

    @Test
    public void usesDepartmentMapper(){
        departmentService.getAllDepartments();
        Mockito.verify(departmentMapper,times(2)).mapDepartmentResponseFromDepartment(any());
    }

    @Test
    public void returnListOfDepartmentResponse(){
        GenericResponse<GetAllDepartmentResponse> output=departmentService.getAllDepartments();
        List<DepartmentResponse> theList;
        theList=output.getData().getDepartmentResponseList();
        Assert.assertEquals(2, theList.size());
        List<DepartmentResponse> expectedList=new ArrayList<>();
        expectedList.add(departmentResponseFromMapper);
        expectedList.add(departmentResponseFromMapper);
        Assert.assertThat(theList, CoreMatchers.hasItems(departmentResponseFromMapper,departmentResponseFromMapper));

    }
}
