package com.example.demo.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.jwt-token")
@Getter
@Setter
public class JwtTokenConfiguration {

    private String tokenSecret;
    private long expiration;
}
