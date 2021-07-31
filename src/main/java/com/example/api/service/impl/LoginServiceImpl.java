package com.example.api.service.impl;

import com.example.api.dao.UserDao;
import com.example.api.dto.*;
import com.example.api.entity.UserEntity;
import com.example.api.service.JwtService;
import com.example.api.service.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserDao userDao;
    private final JwtService jwtService;

    public LoginServiceImpl (final UserDao userDao, final JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    private UserDto getUserDto(final UserEntity userEntity) {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(userEntity.getOrg().getId());
        organizationDto.setCity(userEntity.getOrg().getCity());
        organizationDto.setState(userEntity.getOrg().getState());
        organizationDto.setCountry(userEntity.getOrg().getCountry());
        organizationDto.setName(userEntity.getOrg().getName());
        organizationDto.setSalvations(userEntity.getOrg().getSalvations());
        organizationDto.setOrgUserName(userEntity.getOrg().getOrgUserName());
        organizationDto.setPhoneNumber(userEntity.getOrg().getPhoneNumber());

        List<TaskDto> taskDtos = userEntity.getOrg().getTasks()
                .stream()
                .map(taskEntity -> {
                    TaskDto taskDto = new TaskDto();
                    taskDto.setId(taskEntity.getId());
                    taskDto.setAddress(taskEntity.getAddress());
                    taskDto.setCity(taskEntity.getCity());
                    taskDto.setState(taskEntity.getState());
                    taskDto.setName(taskEntity.getName());
                    taskDto.setStatus(taskEntity.getStatus());
                    taskDto.setSalvations(taskEntity.getSalvations());
                    taskDto.setFirstName(taskEntity.getFirstName());
                    taskDto.setLastName(taskEntity.getLastName());
                    taskDto.setPhoneNumber(taskEntity.getPhoneNumber());

                    OrganizationDto org = new OrganizationDto();
                    org.setId(userEntity.getOrg().getId());
                    taskDto.setOrganization(org);

                    List<CoordinatesDto> coordinatesDtos = taskEntity.getCoordinates()
                            .stream()
                            .map(coordinatesEntity -> {
                                CoordinatesDto coordinatesDto = new CoordinatesDto();
                                coordinatesDto.setLat(coordinatesEntity.getLat());
                                coordinatesDto.setLng(coordinatesEntity.getLng());
                                coordinatesDto.setId(coordinatesEntity.getId());
                                coordinatesDto.setOrderNumber(coordinatesEntity.getOrderNumber());
                                return coordinatesDto;
                            })
                            .collect(Collectors.toList());

                    taskDto.setCoordinates(coordinatesDtos);
                    return taskDto;
                })
                .collect(Collectors.toList());
        organizationDto.setTasks(taskDtos);

        List<UserDto> userDtos = userEntity.getOrg().getUsers()
                .stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setPhoneNumber(user.getPhoneNumber());
                    userDto.setFirstName(user.getFirstName());
                    userDto.setLastName(user.getLastName());
                    userDto.setEmail(user.getEmail());
                    userDto.setRole(user.getRole());
                    return userDto;
                })
                .collect(Collectors.toList());
        organizationDto.setUsers(userDtos);

        UserDto user = new UserDto();
        user.setEmail(userEntity.getEmail());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setId(userEntity.getId());
        user.setOrganization(organizationDto);

        return user;
    }

    private boolean doesPasswordMatch (final String passwordFromDb, final String passwordAttempt) {
        return new BCryptPasswordEncoder().matches(passwordAttempt, passwordFromDb);
    }
}
