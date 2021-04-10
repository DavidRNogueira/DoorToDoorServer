package com.doortodoor.services.impl;

import com.doortodoor.dao.UserDao;
import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.UserDto;
import com.doortodoor.mapper.UserMapper;
import com.doortodoor.services.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    UserDao userDao;

    @Inject
    public UserServiceImpl (UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return new UserDto();
    }

    @Override
    public  UserDto getUserById(UUID id) {
        UserDaoBean userDaoBean = userDao.getUserById(id);

        if (userDaoBean == null) {
            UserDto userDto = new UserDto();
            userDto.setFirstName("WHY");
            return userDto;
        }

        System.out.println("Not null");

        return new UserDto();
    }
}
