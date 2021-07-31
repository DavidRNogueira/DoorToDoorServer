package com.example.api.service;

import com.example.api.dto.LoginDto;
import com.example.api.dto.UserDto;

public interface LoginService {
    UserDto validateLogin (LoginDto loginDto);
    UserDto getUserDetailsByEmail(final String email);
}
