package com.doortodoor.resources;

import com.doortodoor.dto.AuthenticationDto;
import com.doortodoor.dto.UserDto;
import com.doortodoor.services.AuthenticationService;
import com.doortodoor.services.JwtService;
import com.doortodoor.services.UserService;
import com.doortodoor.shared.AuthConstants;
import io.jsonwebtoken.Claims;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.servlet.http.Cookie;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/auth")
public class AuthenticationResource {
    JwtService jwtService;
    AuthenticationService authenticationService;
    UserService userService;

    @Inject
    public AuthenticationResource (final JwtService jwtService, final AuthenticationService authenticationService, final UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @GET
    @Path("user/{id}")
    public UserDto getUserById (@PathParam("id") final UUID id){
        return userService.getUserById(id);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto login (@Valid final AuthenticationDto authenticationDto, final HttpServletRequest request, final HttpServletResponse response) {
        UserDto user = authenticationService.login(authenticationDto);
//        response.addCookie(buildCookie(user.getJwt(), request.getServerName()));
        return user;
    }

    @POST
    @Path("/logout")
    public void logout (final HttpServletRequest request, final HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies[0]);

        for (Cookie cookie: cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    @GET
    @Path("/user-details")
    public UserDto getUserDetails(final HttpServletRequest request) {
        String jwt = jwtService.findAuthJwt(request.getCookies());
        Claims jwtClaims = jwtService.decodeJWT(jwt);

        if (jwtClaims == null) {
            throw new NotAuthorizedException("User not logged in");
        }

        UserDto userDetails =  userService.getUserByEmail(jwtClaims.getId());
        return userDetails;
    }

    @GET
    @Path("/check")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean loginCheck(final HttpServletRequest request) {
        try {
            String jwt = jwtService.findAuthJwt(request.getCookies());
            Claims jwtClaims = jwtService.decodeJWT(jwt);
            if (jwtClaims == null) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            // todo replace with logging library
            System.out.println("Login check threw error");
            return false;
        }
    }

    private Cookie buildCookie(final String jwt, final String origin) {
        Cookie cookie = new Cookie(AuthConstants.AUTH_COOKIE, jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setDomain(origin);
        cookie.setMaxAge(Math.toIntExact(AuthConstants.LOGIN_DURATION));
        return cookie;
    }
}
