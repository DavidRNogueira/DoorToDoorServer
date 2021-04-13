package com.doortodoor.services.impl;

import com.doortodoor.dao.OrganizationDao;
import com.doortodoor.dao.UserDao;
import com.doortodoor.dao.bean.OrganizationDaoBean;
import com.doortodoor.dao.bean.UserDaoBean;
import com.doortodoor.dto.AuthenticationDto;
import com.doortodoor.dto.OrganizationDto;
import com.doortodoor.dto.UserDto;
import com.doortodoor.mapper.OrganizationMapper;
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
    UserMapper userMapper;
    OrganizationDao organizationDao;
    OrganizationMapper organizationMapper;

    @Inject
    public AuthenticationServiceImpl (
            final UserDao userDao,
            final JwtService jwtService,
            final UserMapper userMapper,
            final OrganizationDao organizationDao,
            final OrganizationMapper organizationMapper) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
        this.organizationDao = organizationDao;
        this.organizationMapper = organizationMapper;
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

        // Temporary
        if(!authenticationDto.getPassword().equals(userDaoBean.getPassword())) {
            throw new NotAcceptableException();
        }

        userDaoBean.setPassword(null);
        UserDto userDto = userMapper.userDaoBeanToDto(userDaoBean);

        String jwt = jwtService.createJWT(userDto.getEmail());
        userDto.setJwt(jwt);

        OrganizationDaoBean organizationDaoBean = organizationDao.getOrganizationById(userDaoBean.getOrganizationFk());
        OrganizationDto organizationDto = organizationMapper.organizationDaoBeanToDto(organizationDaoBean);
        userDto.setOrganization(organizationDto);

        return userDto;
    }

    private boolean doesPasswordMatch (final String passwordFromDb, final String passwordAttempt) {
        return new BCryptPasswordEncoder().matches(passwordAttempt, passwordFromDb);
    }
}
