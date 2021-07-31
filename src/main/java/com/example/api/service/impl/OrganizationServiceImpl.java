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

    public void createOrg(OrganizationDto organizationDto) {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(organizationDto.getName());
        organizationEntity.setCity(organizationDto.getCity());
        organizationEntity.setState(organizationDto.getState());
        organizationEntity.setCountry(organizationDto.getCountry());
        organizationEntity.setOrgUserName(organizationDto.getOrgUserName().toLowerCase());
        organizationEntity.setPhoneNumber(organizationDto.getPhoneNumber());
        orgDao.createOrg(organizationEntity);
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

    public OrganizationDto getOrgById (String id) {
        OrganizationEntity organizationEntity = orgDao.getOrgById(id);
        OrganizationDto organizationDto = new OrganizationDto();

        organizationDto.setPhoneNumber(organizationEntity.getPhoneNumber());
        organizationDto.setName(organizationEntity.getName());
        organizationDto.setCity(organizationEntity.getCity());
        organizationDto.setState(organizationEntity.getState());
        organizationDto.setCountry(organizationEntity.getCountry());
        organizationDto.setId(id);
        organizationDto.setOrgUserName(organizationEntity.getOrgUserName());

        List<TaskDto> taskDtos = organizationEntity.getTasks()
                .stream()
                .map(taskEntity -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setId(taskEntity.getId());
                    taskDto.setAddress(taskEntity.getAddress());
                    taskDto.setCity(taskEntity.getCity());
                    taskDto.setState(taskEntity.getState());
                    taskDto.setName(taskEntity.getName());

                    List<CoordinatesDto> coordinatesDtos = taskEntity.getCoordinates()
                            .stream()
                            .map(coordinatesEntity -> {
                                CoordinatesDto coordinatesDto = new CoordinatesDto();
                                coordinatesDto.setLat(coordinatesEntity.getLat());
                                coordinatesDto.setLng(coordinatesEntity.getLng());
                                coordinatesDto.setId(coordinatesEntity.getId());
                                return coordinatesDto;
                            })
                            .collect(Collectors.toList());

                    taskDto.setCoordinates(coordinatesDtos);
                    return taskDto;
                })
                .collect(Collectors.toList());
        organizationDto.setTasks(taskDtos);

        List<UserDto> userDtos = organizationEntity.getUsers()
                .stream()
                .map(userEntity -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(userEntity.getId());
                    userDto.setPhoneNumber(userEntity.getPhoneNumber());
                    userDto.setFirstName(userEntity.getFirstName());
                    userDto.setLastName(userEntity.getLastName());
                    userDto.setEmail(userEntity.getEmail());
                    userDto.setRole(userEntity.getRole());
                    return userDto;
                })
                .collect(Collectors.toList());
        organizationDto.setUsers(userDtos);

        return organizationDto;
    }
}
