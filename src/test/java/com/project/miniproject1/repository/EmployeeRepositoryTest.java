package com.project.miniproject1.repository;

import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.entity.Employee;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private ModelMapper modelMapper;
    private Employee employee;

    @BeforeEach
    public  void setup(){
        employee=Employee.builder()
                .firstname("john")
                .lastname("doe")
                .email("test@123")
                .build();
    }

    @DisplayName("Test an Employee getting saved in DB")
    @Test
    public void givenEmployee_whenSaved_returnSavedEmployee(){
        Employee savedEmployee=employeeRepo.save(employee);
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("Test an Employee getting converted to DTO class")
    @Test
    public void testEntityToDtoConversion() {
        // Create an Order entity


        // Convert the entity to DTO
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        // Assert that the DTO contains the expected values
        assertNotNull(employeeDto);
        assertEquals("john", employeeDto.getFirstname());
        assertEquals("doe", employeeDto.getLastname());
        assertEquals("test@123", employeeDto.getEmail());

    }

    @DisplayName("Test when given an list of employee return the List of employee")
    @Test
    public void givenEmployeeList_whenFindAll_returnSavedEmployeeList(){
        Employee employee1=Employee.builder()
                .firstname("Peter")
                .lastname("Hill")
                .email("Peter@123")
                .build();
        employeeRepo.save(employee);
        employeeRepo.save(employee1);
        List<Employee> employeeList=employeeRepo.findAll();
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    @DisplayName("Test when given email return employee detail")
    @Test
    public void givenEmployeeEmail_WhenFindByEmail_ThenReturnEmployee(){
        employeeRepo.save(employee);
        Employee employeeDB=employeeRepo.findByEmail(employee.getEmail()).get();
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("Test when given employee ID and employeeInfo update the employee details")
    @Test
    public void givenEmployee_WhenUpdateEmployee_ThenReturnUpdatedEmployee(){
        employeeRepo.save(employee);
        Employee savedmployee=employeeRepo.findById(employee.getId()).get();
        savedmployee.setEmail("john@gmail.com");
        Employee updatedEmployee=employeeRepo.save(savedmployee);
        assertThat(updatedEmployee.getEmail()).isEqualTo("john@gmail.com");
    }

    @DisplayName("Test when given employee details to delete removes info from database")
    @Test
    public void givenEmployee_WhenDeleteEmployee_ThenRemoveEmployee() {
        employeeRepo.save(employee);
        employeeRepo.deleteById(employee.getId());
        Optional<Employee> optionalEmployee=employeeRepo.findById(employee.getId());
        assertThat(optionalEmployee).isEmpty();
    }

    @DisplayName("Test when given an employee ID return the Employee from DB")
    @Test
    public void givenEmployeeId_whenFind_returnSavedEmployee(){
        employeeRepo.save(employee);
        Employee savedemployee=employeeRepo.findById(employee.getId()).get();
        assertThat(savedemployee).isNotNull();

    }



}

