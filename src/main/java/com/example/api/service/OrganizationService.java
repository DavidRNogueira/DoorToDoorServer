package com.example.api.service;

import com.example.api.dto.OrganizationDto;

public interface OrganizationService {
    OrganizationDto isOrgUserNameAvailable (String orgUserName);
    OrganizationDto getOrgById (String id);
}
