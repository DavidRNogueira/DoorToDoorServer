package com.example.api.service;

import com.example.api.dto.OrganizationDto;

public interface OrganizationService {
    void createOrg (OrganizationDto organizationDto);
    OrganizationDto isOrgUserNameAvailable (String orgUserName);
    OrganizationDto getOrgById (String id);
}
