package com.project.miniproject1.controller;

import com.project.miniproject1.dto.EmployeeDto;
import com.project.miniproject1.dto.JwtAuthResponse;
import com.project.miniproject1.dto.LoginDto;
import com.project.miniproject1.dto.RegisterDto;
import com.project.miniproject1.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
//End Points to register User
@RequestMapping("api/auth")
public class AuthController {
    AuthService authService;
    //Register Controller to get User detail and Process it
    @PostMapping("/register")
    public String register(@RequestBody RegisterDto registerDto){
        String registermessage=authService.register(registerDto);
        return registermessage;
    }
    //Login Controller to get Login detail and Process it
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        JwtAuthResponse jwtAuthResponse =authService.login(loginDto);
        return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
    }
}
