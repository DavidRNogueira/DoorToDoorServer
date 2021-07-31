package com.example.api.dao;

import com.example.api.entity.UserEntity;

public interface UserDao {
    UserEntity getUserByEmail (String email);
    UserEntity getUserById (String id);
    UserEntity createNewUser (UserEntity userEntity);
    void deleteUserById (String id);
}
