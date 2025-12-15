package com.stevenjtk.trivia.security.jwt;

import com.stevenjtk.trivia.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.management.relation.Role;
import javax.xml.crypto.Data;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.stevenjtk.trivia.entity.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final String base64EncodedSecretKey = "w4F7n8YtRzP9sX1vKj5Lq6Mv2BdVfG7hQyZrNc8Ua0E=";
    private final SecretKey key;
    private final long jwtExpirationMS = TimeUnit.HOURS.toMillis(1);

    public JwtUtil() {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedSecretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public SecretKey getKey() {
        return key;
    }

    public long getJwtExpirationMS() {
        return jwtExpirationMS;
    }

    public String generateToken(User user) {
        List<String> roles = user.getAllRoles().stream()
                .map(Roles::getRoleName)
                .toList();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", user.getAllRoles().stream()
                        .map(Roles::getRoleName)
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + getJwtExpirationMS()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return jws.getBody().getSubject();

        } catch (JwtException e) {
            logger.warn("Failed to extract username from token: {}", token);
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            logger.error("Failed to validate token: {}", e.getMessage());
            return false;
        }
    }
}
