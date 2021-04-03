package com.doortodoor.services;

import com.doortodoor.dto.AuthenticationDto;
import com.doortodoor.dto.UserDto;

public interface AuthenticationService {
    UserDto login(AuthenticationDto authenticationDto);
    void testInjection();
}
