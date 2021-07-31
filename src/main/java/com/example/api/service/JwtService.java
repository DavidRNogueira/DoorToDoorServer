package com.example.api.service;

import io.jsonwebtoken.Claims;

import javax.servlet.http.Cookie;

public interface JwtService {
    String createJWT(String username);
    Claims decodeJWT (String jwt);
    String findAuthJwt(final Cookie[] cookies);
}
