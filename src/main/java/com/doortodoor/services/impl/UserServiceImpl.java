package com.doortodoor.services.impl;

import com.doortodoor.dto.UserDto;
import com.doortodoor.services.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public UserDto getUserByEmail(String email) {
        return new UserDto();
    }
}
