package com.project.miniproject1.service;

import com.project.miniproject1.dto.JwtAuthResponse;
import com.project.miniproject1.dto.LoginDto;
import com.project.miniproject1.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
}
