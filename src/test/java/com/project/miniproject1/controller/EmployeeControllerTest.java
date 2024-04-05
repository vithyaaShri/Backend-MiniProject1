package com.project.miniproject1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.entity.Employee;
import com.project.miniproject1.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();
    private Employee employee;

    @BeforeEach
    public void setup() {

        employee = Employee.builder()
                .id(1L)
                .firstname("john")
                .lastname("doe")
                .email("test@123")
                .build();
    }

    @Test
    public void givenEmployee_whenCreatedEmployee_ReturnEmployee() throws Exception {

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        given(employeeService.createEmployee(any(EmployeeDto.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", is(employeeDto.getFirstname())))
                .andExpect(jsonPath("$.lastname", is(employeeDto.getLastname())))
                .andExpect(jsonPath("$.email", is(employeeDto.getEmail())));

    }
}
//    @Test
//    public void givenEmployee_whenUpdatedEmployee_ReturnUpdateEmployee() throws Exception {
//        long empid=1L;
//        Employee updatedEmployee=Employee.builder()
//                .id(1L)
//                .firstname("peter")
//                .lastname("doe")
//                .email("peter@123")
//                .build();
//        EmployeeDto employeeDto=modelMapper.map(updatedEmployee,EmployeeDto.class);
//        given(employeeService.getEmployeeById(empid)).willReturn(Optional.empty());
//        given(employeeService.updateEmployee(any(EmployeeDto.class)))
//                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
//        ResultActions response=mockMvc.perform(put("/api/employee/{id}",empid)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(employeeDto)));
//        response.andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.firstname",is(employeeDto.getFirstname())))
//                .andExpect(jsonPath("$.lastname",is(employeeDto.getLastname())))
//                .andExpect(jsonPath("$.email",is(employeeDto.getEmail())));
//    }
//}
