package com.project.miniproject1.service.Impl;

import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.entity.Employee;
import com.project.miniproject1.exception.EmailAlreadyExsist;
import com.project.miniproject1.exception.ResourceNotFoundException;
import com.project.miniproject1.repository.EmployeeRepo;
import com.project.miniproject1.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    ModelMapper modelMapper;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee=employeeRepo.findByEmail(employeeDto.getEmail());
        if(optionalEmployee.isPresent()){
            throw  new EmailAlreadyExsist("Email Already Exsist");
        }
        Employee employee=modelMapper.map(employeeDto,Employee.class);
        Employee savedEmployee=employeeRepo.save(employee);
        return modelMapper.map(savedEmployee,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee=modelMapper.map(employeeDto,Employee.class);
        Employee exsistingUser=employeeRepo.findById(employee.getId())
                .orElseThrow(()->new ResourceNotFoundException("Employee","id",employee.getId()));
        exsistingUser.setFirstname(employee.getFirstname());
        exsistingUser.setLastname(employee.getLastname());
        exsistingUser.setEmail(employee.getEmail());
        Employee updatedEmployee=employeeRepo.save(exsistingUser);

        return modelMapper.map(updatedEmployee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
    List<Employee> employees=employeeRepo.findAll();
    List<EmployeeDto> employeeDtos=new ArrayList<>();
    employees.forEach(employee->employeeDtos.add(modelMapper.map(employee,EmployeeDto.class)));
    return employeeDtos;
    }


    @Override
    public void deleteEmployee(Long id) {
        Employee exsistingUser=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee","id",id));
        employeeRepo.delete(exsistingUser);

    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee exsistingUser=employeeRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee","id",id));
        return modelMapper.map(exsistingUser,EmployeeDto.class);
    }
}
