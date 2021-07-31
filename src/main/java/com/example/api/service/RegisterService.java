package com.example.api.service;

import com.example.api.dto.OrganizationDto;
import com.example.api.dto.UserDto;

public interface RegisterService {
    void registerOrg (OrganizationDto organizationDto);
    UserDto registerUser (UserDto userDto);
}
