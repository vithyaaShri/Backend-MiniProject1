package com.project.miniproject1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExsist extends RuntimeException{
    private  String message;
    public  EmailAlreadyExsist(String message){
        super(message);
    }
}
