package com.doortodoor.services;

import com.doortodoor.dto.UserDto;

public interface UserService {
    UserDto getUserByEmail(String email);
}
