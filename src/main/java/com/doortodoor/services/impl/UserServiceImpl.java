package com.doortodoor.services.impl;

import com.doortodoor.dao.UserDao;
import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.UserDto;
import com.doortodoor.mapper.UserMapper;
import com.doortodoor.services.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    UserDao userDao;
    UserMapper userMapper;

    @Inject
    public UserServiceImpl (final UserDao userDao, final UserMapper userMapper){
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return new UserDto();
    }

    @Override
    public UserDto getUserById(UUID id) {
        UserDaoBean userDaoBean = userDao.getUserById(id);
        System.out.println(userDaoBean.getEmail());
        if (userDaoBean == null) {
            throw new NotFoundException();
        }

        return userMapper.userDaoBeanToDto(userDaoBean);
    }
}
