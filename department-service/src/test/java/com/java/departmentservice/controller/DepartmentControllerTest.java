package com.java.departmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.departmentservice.entity.Department;
import com.java.departmentservice.repository.DepartmentRepository;
import com.java.departmentservice.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

@Mock
DepartmentService departmentService;

@InjectMocks
DepartmentController departmentController;

private MockMvc mockMvc;

Department dp1 = new Department(1L,"IT","Prahladnagar","IT");
Department dp2 = new Department(2L,"Mechanical","SG Highway","MECH");
Department dp3 = new Department(3L,"Civil","Shella","CIVIL");

@Before
public void setup() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
}

@Test
public void getAllDepartment() throws Exception{
    ArrayList<Department> departments = new ArrayList<>(Arrays.asList(dp1,dp2,dp3));
    Mockito.when(departmentService.findDepartments()).thenReturn(departments);

    mockMvc.perform(MockMvcRequestBuilders.get("/departments/").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentName",is("IT")));

}

@Test
public void findDepartmentById() throws Exception{
    Mockito.when(departmentService.findDepartmentById(dp2.getDepartmentId())).thenReturn(dp2);

    mockMvc.perform(MockMvcRequestBuilders.get("/departments/2").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.departmentCode",is("MECH")));
}

    @Test
    public void findDepartmentById1() throws Exception{
        Mockito.when(departmentService.findDepartmentById(dp2.getDepartmentId())).thenReturn(dp2);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.departmentCode",is("MECH")));
    }

@Test
public void saveDepartment() throws Exception{
    Department department = new Department(4L,"TestDP","TestPlace","TEST");

    String departmentStr = objectMapper.writeValueAsString(department);
    Mockito.when(departmentService.saveDepartment(department)).thenReturn(department);

    mockMvc.perform(MockMvcRequestBuilders.post("/departments/saveDepartment").contentType(MediaType.APPLICATION_JSON).content(departmentStr))
    .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.departmentCode",is("TEST")));

}
}
