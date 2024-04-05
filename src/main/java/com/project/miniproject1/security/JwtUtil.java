package com.project.miniproject1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {
    //get Expiration
    private static final int expiryInMs=300*60*60;
    private static final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public  String generate(String username){
        return Jwts.builder().setSubject(username).setIssuer("test.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiryInMs))
                .signWith(key).compact();
    }
    //Validate Token

    public boolean validate(String token){
        if(isExpired(token) && getUserName(token)!=null){
            return true;
        }
        return false;
    }
    //Validate Logged in User
    public  String getUserName(String token){
        Claims claims=getClaims(token);
        return claims.getSubject();
    }

    public boolean isExpired(String token){
        Claims claims=getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));

    }
    public Claims getClaims(String token){
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();

    }
}
