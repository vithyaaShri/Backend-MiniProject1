package com.project.miniproject1.service;

import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.entity.Employee;
import com.project.miniproject1.exception.EmailAlreadyExsist;
import com.project.miniproject1.exception.ResourceNotFoundException;
import com.project.miniproject1.repository.EmployeeRepo;
import com.project.miniproject1.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    private Employee employee;
    @Spy
    private ModelMapper modelMapper=new ModelMapper();
    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {

        employee = Employee.builder()
                .id(1L)
                .firstname("john")
                .lastname("doe")
                .email("test@123")
                .build();
    }
    @DisplayName("Test when an Employee Given Create employee in DB")
    @Test
    public void giveEmployee_whenCreateEmployee_thenReturnEmployee() {
        given(employeeRepo.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepo.save(employee)).willReturn(employee);
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        EmployeeDto savedemployeeDto = employeeService.createEmployee(employeeDto);
        Employee savedemployee = modelMapper.map(savedemployeeDto, Employee.class);
        assertThat(savedemployee).isNotNull();
    }
    @DisplayName("Test when an Employee with existing email Given throws Email AlreadyExist error")
    @Test
    public void giveEmployee_whenExistingEmail_thenThrowEmailAlreadyExistException() {
        given(employeeRepo.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        assertThrows(EmailAlreadyExsist.class,()->employeeService.createEmployee(employeeDto));
        verify(employeeRepo, never()).save(any(Employee.class));
    }
    @DisplayName("Test when an List of Employee Given returns List Of employee")
    @Test
    public void giveEmployeeList_whenGetAllEmployee_thenReturnEmployeeList() {
        Employee employee1 = Employee.builder()
                .firstname("Peter")
                .lastname("Hill")
                .email("Peter@123")
                .build();
        given(employeeRepo.findAll()).willReturn(List.of(employee1,employee));
        List<EmployeeDto> employeeDtos=employeeService.getAllEmployee();
        assertThat(employeeDtos).isNotNull();
        assertThat(employeeDtos.size()).isGreaterThan(0);
    }
    @DisplayName("Test when an List of Employee Given returns List Of employee")
    @Test
    public void giveEmployeeId_whenGetEmployee_thenReturnEmployee() {
        given(employeeRepo.findById(employee.getId())).willReturn(Optional.of(employee));
        EmployeeDto employeeDto=employeeService.getEmployeeById(employee.getId());
        Employee employee1=modelMapper.map(employeeDto,Employee.class);
        assertThat(employee1).isNotNull();
    }
    @DisplayName("Test when an Employee id Given returns Updated employee")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        given(employeeRepo.save(employee)).willReturn(employee);
        given(employeeRepo.findById(employee.getId())).willReturn(Optional.of(employee));
        employee.setEmail("john@gmail.com");
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        EmployeeDto savedemployeeDto = employeeService.updateEmployee(employeeDto);
        Employee savedemployee = modelMapper.map(savedemployeeDto, Employee.class);
        assertThat(savedemployee.getEmail()).isEqualTo("john@gmail.com");
    }

    @DisplayName("Test when an Employee id Given delete employee")
    @Test
    public void givenEmployee_whenDeleteEmployee_thenReturnRemoveEmployee(){
        long employeeId = employee.getId();
        Employee employeeToDelete = new Employee();
        employeeToDelete.setId(employeeId);

        // Mock the delete operation
        willDoNothing().given(employeeRepo).deleteById(employeeId);
        // Call the service method
        employeeService.deleteEmployee(employeeId);

        // Then
        // Verify that the delete method was called with the correct ID
        verify(employeeRepo).deleteById(employeeId);

    }
}

