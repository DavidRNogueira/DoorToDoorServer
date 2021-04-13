package com.doortodoor.services;

import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dto.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto getUserByEmail(String email);
    UserDto getUserById(UUID id);
    UUID createUser(UserDto userDto);
}
