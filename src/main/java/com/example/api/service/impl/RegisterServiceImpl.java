package com.example.api.service.impl;

import com.example.api.dao.OrgDao;
import com.example.api.dao.UserDao;
import com.example.api.dto.CoordinatesDto;
import com.example.api.dto.OrganizationDto;
import com.example.api.dto.TaskDto;
import com.example.api.dto.UserDto;
import com.example.api.entity.OrganizationEntity;
import com.example.api.entity.UserEntity;
import com.example.api.service.RegisterService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl implements RegisterService {
    private UserDao userDao;
    private OrgDao orgDao;

    public RegisterServiceImpl (UserDao userDao, OrgDao orgDao) {
        this.userDao = userDao;
        this.orgDao = orgDao;
    }

    public void registerOrg (OrganizationDto organizationDto) {
        return;
    }

    public UserDto registerUser (UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        OrganizationEntity organizationEntity;

        if ((userDto.getOrganization() != null && userDto.getOrganization().getId() != null) || userDto.getOrgUserName().length() > 0) {
            if (userDto.getOrganization() != null && userDto.getOrganization().getId() != null) {
                organizationEntity = orgDao.getOrgById(userDto.getOrganization().getId());
            } else {
                organizationEntity = orgDao.findOrgByOrgUserName(userDto.getOrgUserName());
            }
        } else {
            throw new BadRequestException("We are missing info to search for user.");
        }

        if (userDto.getPassword() != null) {
            String encryptPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
            userEntity.setPassword(encryptPassword);
        }

        userEntity.setEmail(userDto.getEmail());
        userEntity.setPhoneNumber(userDto.getPhoneNumber());
        userEntity.setOrg(organizationEntity);
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setRole(userDto.getRole());

        UserEntity createdUser = userDao.createNewUser(userEntity);

        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organizationEntity.getId());
        organizationDto.setCity(organizationEntity.getCity());
        organizationDto.setState(organizationEntity.getState());
        organizationDto.setCountry(organizationEntity.getCountry());
        organizationDto.setName(organizationEntity.getName());
        organizationDto.setOrgUserName(organizationEntity.getOrgUserName());
        organizationDto.setPhoneNumber(organizationEntity.getPhoneNumber());

        List<UserDto> userDtoList = organizationEntity.getUsers()
                .stream()
                .map(user -> {
                    UserDto userDto1 = new UserDto();
                    userDto1.setRole(user.getRole());
                    userDto1.setEmail(user.getEmail());
                    userDto1.setFirstName(user.getFirstName());
                    userDto1.setLastName(user.getLastName());
                    userDto1.setPhoneNumber(user.getPhoneNumber());
                    userDto1.setId(user.getId());
                    return userDto1;
                })
                .collect(Collectors.toList());
        organizationDto.setUsers(userDtoList);

        List<TaskDto> taskDtos = userEntity.getOrg().getTasks()
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

        UserDto returnUser = new UserDto();
        returnUser.setEmail(createdUser.getEmail());
        returnUser.setFirstName(createdUser.getFirstName());
        returnUser.setLastName(createdUser.getLastName());
        returnUser.setPhoneNumber(createdUser.getPhoneNumber());
        returnUser.setId(createdUser.getId());
        returnUser.setRole(createdUser.getRole());
        returnUser.setOrganization(organizationDto);
        return returnUser;


    }
}
