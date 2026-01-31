package org.example.onlinebankmanagementbackend.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.example.onlinebankmanagementbackend.security.config.JwtProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static JwtProperties jwtProperties = null;

    public JwtUtil(JwtProperties jwtProperties)
    {
        JwtUtil.jwtProperties = jwtProperties;
    }

    public String generateToken(String userId, Map<String, Object> claims)
    {
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtProperties.getExpiration())
                )
                .signWith(
                        Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public static Claims validateToken(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
