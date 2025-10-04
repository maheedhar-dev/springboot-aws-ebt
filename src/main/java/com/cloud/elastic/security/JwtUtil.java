package com.cloud.elastic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username,String role){
        return Jwts.builder().setSubject(username).claim("role",role).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(key)
                .compact();
    }
    private Claims parseClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token){
        return parseClaims(token).getSubject();
    }

    public String extractRole(String token){
        return parseClaims(token).get("role",String.class);
    }

    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        }catch (JwtException ex){
            return false;
        }
    }
}
