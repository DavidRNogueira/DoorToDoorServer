package com.example.api.service.impl;

import com.example.api.dao.OrgDao;
import com.example.api.dto.CoordinatesDto;
import com.example.api.dto.OrganizationDto;
import com.example.api.dto.TaskDto;
import com.example.api.dto.UserDto;
import com.example.api.entity.OrganizationEntity;
import com.example.api.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private OrgDao orgDao;

    public OrganizationServiceImpl (OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    @Override
    public OrganizationDto isOrgUserNameAvailable(String orgUserName) {
        OrganizationEntity organizationEntity = orgDao.findOrgByOrgUserName(orgUserName.toLowerCase());

        if (organizationEntity == null) {
            return null;
        }

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setName(organizationEntity.getName());
        organizationDto.setCity(organizationEntity.getCity());
        organizationDto.setState(organizationEntity.getState());
        organizationDto.setCountry(organizationEntity.getCountry());
        organizationDto.setId(organizationEntity.getId());
        return organizationDto;
    }
}
