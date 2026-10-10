package com.firstProgram.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils{
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username){
        Map<String,Object>claims=new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 5)
                )
                .signWith(getSignInKey())
                .compact();
    }
    private Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }
    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }
    public Boolean validateToken(String token){
        return  !isTokenExpired(token);
    }

}
