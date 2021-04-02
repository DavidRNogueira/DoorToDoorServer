package com.doortodoor.services.impl;

import com.doortodoor.services.JwtService;
import com.doortodoor.shared.AuthConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JwtServiceImpl implements JwtService {

    @Override
    public String createJWT(String email) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // sign key
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(System.getenv("JWT_KEY"));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(email)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + AuthConstants.LOGIN_DURATION;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        return builder.compact();
    }

    @Override
    public Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(System.getenv("JWT_KEY")))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    @Override
    public String findAuthJwt(final Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }

        for (Cookie aCookie : cookies) {
            String name = aCookie.getName();
            String value = aCookie.getValue();

            if (name.equals(AuthConstants.AUTH_COOKIE)) {
                return value;
            }
        }
        return null;
    }
}