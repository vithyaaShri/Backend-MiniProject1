package com.project.miniproject1.controller;

import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.dto.LoginDto;
import com.project.miniproject1.security.JwtUtil;
import com.project.miniproject1.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@CrossOrigin("*")
//End Point for Employee
@RequestMapping("/api/employee")
public class EmployeeController {
    EmployeeService employeeService;
    //Post Mapping to Save Employee Detail from repository
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee){
        EmployeeDto savedEmployee=employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }
    //Get Mapping to Get All employee Detail
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        List<EmployeeDto> employees=employeeService.getAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    //Get Mapping to get Employee Detail based On id
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("id") Long id){
        EmployeeDto Employee=employeeService.getEmployeeById(id);
        return new ResponseEntity<>(Employee, HttpStatus.OK);
    }
    //Delete Mapping to delete Based On Employee Id

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee Successfully deleted",HttpStatus.NO_CONTENT);
    }
    //Update in backend Based on Employee Id
    @PutMapping("{id}")
    public ResponseEntity<EmployeeDto> UpdateUser(@PathVariable("id") Long id,@RequestBody @Valid EmployeeDto employee){
        employee.setId(id);
        EmployeeDto updatedEmployee=employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.ACCEPTED);
    }

}
