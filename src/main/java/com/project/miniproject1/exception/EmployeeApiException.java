package com.project.miniproject1.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EmployeeApiException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
}
