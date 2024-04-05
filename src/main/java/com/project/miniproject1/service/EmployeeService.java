package com.project.miniproject1.service;

import com.project.miniproject1.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee();
    void deleteEmployee(Long id);
    EmployeeDto getEmployeeById(Long id);
}
