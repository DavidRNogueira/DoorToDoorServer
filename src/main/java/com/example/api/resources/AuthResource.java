package com.example.api.resources;

import com.example.api.dto.LoginDto;
import com.example.api.dto.UserDto;
import com.example.api.service.JwtService;
import com.example.api.service.RegisterService;
import com.example.api.service.impl.LoginServiceImpl;
import com.example.api.shared.AuthConstants;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.NotAuthorizedException;

@RestController
@RequestMapping("/auth")
public class AuthResource {
    private final LoginServiceImpl loginService;
    private final RegisterService registerService;
    private final JwtService jwtService;

    public AuthResource(final LoginServiceImpl loginService, final RegisterService registerService, final JwtService jwtService) {
        this.loginService = loginService;
        this.registerService = registerService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public UserDto login (@RequestBody @Valid final LoginDto loginDto, final HttpServletRequest request, final HttpServletResponse response) {
        UserDto user = loginService.validateLogin(loginDto);
        response.addCookie(buildCookie(user.getJwt(), request.getServerName()));
        return user;
    }

    private Cookie buildCookie(final String jwt, final String origin) {
        Cookie cookie = new Cookie(AuthConstants.AUTH_COOKIE, jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setDomain(origin);
        cookie.setMaxAge(Math.toIntExact(AuthConstants.LOGIN_DURATION));
        return cookie;
    }

    @PostMapping("/logout")
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

    @GetMapping("/user-details")
    public UserDto getUserDetails(final HttpServletRequest request) {
        String jwt = jwtService.findAuthJwt(request.getCookies());
        Claims jwtClaims = jwtService.decodeJWT(jwt);

        if (jwtClaims == null) {
            throw new NotAuthorizedException("User not logged in");
        }

        UserDto userDetails =  loginService.getUserDetailsByEmail(jwtClaims.getId());
        return userDetails;
    }

    @GetMapping("/check")
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

    @PostMapping("/create-user")
    public UserDto register (@RequestBody @Valid final UserDto userDto) {
        return registerService.registerUser(userDto);
    }
}

