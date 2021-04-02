package com.doortodoor.services.impl;

import com.doortodoor.dto.AuthenticationDto;
import com.doortodoor.dto.UserDto;
import com.doortodoor.services.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public UserDto login(AuthenticationDto authenticationDto) {
        return new UserDto();
    }
}
