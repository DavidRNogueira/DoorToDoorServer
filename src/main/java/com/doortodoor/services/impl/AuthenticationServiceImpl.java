package com.doortodoor.services.impl;

import com.doortodoor.dao.UserDao;
import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.AuthenticationDto;
import com.doortodoor.dto.UserDto;
import com.doortodoor.mapper.UserMapper;
import com.doortodoor.services.AuthenticationService;
import com.doortodoor.services.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {
    UserDao userDao;
    JwtService jwtService;

    @Inject
    public AuthenticationServiceImpl (final UserDao userDao, final JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    @Override
    public UserDto login(AuthenticationDto authenticationDto) {
        UserDaoBean userDaoBean = userDao.getUserByEmail(authenticationDto.getEmail());

        if (userDaoBean == null) {
            throw new NotAuthorizedException("Login failed");
        }

//        if (!doesPasswordMatch(userDaoBean.getPassword(), authenticationDto.getPassword())) {
//            throw new NotAcceptableException();
//        }

        if(!authenticationDto.getPassword().equals(userDaoBean.getPassword())) {
            throw new NotAcceptableException();
        }

        UserDto userDto = UserMapper.INSTANCE.userBeanToDto( userDaoBean );

        String jwt = jwtService.createJWT(userDto.getEmail());
        userDto.setJwt(jwt);

        return userDto;
    }

    private boolean doesPasswordMatch (final String passwordFromDb, final String passwordAttempt) {
        return new BCryptPasswordEncoder().matches(passwordAttempt, passwordFromDb);
    }
}
