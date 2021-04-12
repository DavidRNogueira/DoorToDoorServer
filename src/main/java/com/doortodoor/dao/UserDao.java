package com.doortodoor.dao;

import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;

import java.util.UUID;

public interface UserDao {
    UserDaoBean getUserByEmail(String email);
    UserDaoBean getUserById (UUID id);
}
