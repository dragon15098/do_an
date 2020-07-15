package com.example.demo.configuration.filters;

import com.example.demo.configuration.properties.AuthenticationHeaderConfiguration;
import com.example.demo.configuration.properties.JwtTokenConfiguration;
import com.example.demo.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final AuthenticationHeaderConfiguration authenticationHeaderConfiguration;
    private final JwtTokenConfiguration jwtTokenConfiguration;


    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtTokenConfiguration.getExpiration());
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtTokenConfiguration.getTokenSecret())
                .compact();
    }

    public boolean notContainsToken(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeader(request);
        return authorizationHeader == null || !authorizationHeader.startsWith(authenticationHeaderConfiguration.getPrefix()) || authorizationHeader.equals("Bearer");
    }

    public String getSubject(HttpServletRequest request) {
        String authorizationHeader = getAuthorizationHeader(request);
        String token = authorizationHeader.replaceAll(authenticationHeaderConfiguration.getPrefix(), "").trim();
        return Jwts.parser()
                .setSigningKey(jwtTokenConfiguration.getTokenSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(authenticationHeaderConfiguration.getName());
    }
}
