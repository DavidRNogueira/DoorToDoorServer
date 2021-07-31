package com.example.api.service.impl;

import com.example.api.dao.UserDao;
import com.example.api.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl (UserDao userDao) {
        this.userDao = userDao;
    }

    public void deleteUser (String id) {
        userDao.deleteUserById(id);
    }
}
