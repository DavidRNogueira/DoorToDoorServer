package com.doortodoor.dao;

import com.doortodoor.dao.bean.UserDaoBean;

public interface UserDao {
    UserDaoBean getUserByEmail(String email);
}
