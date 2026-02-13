package com.mindingmybookness.Service;

import com.mindingmybookness.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtService {

    String SECRET_KEY = "cml2ZXJleGFjdGx5Y29uc2lkZXJpbXBvcnRhbmNlc3VnYXJsYXN0d2hhdHdlaWdoZmw=";

    public String generateToken(User user){

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token){

        try{
            extractclaim(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }



    public String extractUsername(String token){
        return extractclaim(token).getSubject();
    }



    private Claims extractclaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }


    private Key getSignInKey(){
        byte[]  keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
