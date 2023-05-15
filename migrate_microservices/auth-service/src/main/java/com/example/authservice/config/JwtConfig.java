package com.example.authservice.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {

    @Value("${gateway.app.route.auth}")
    private String Uri;

    @Value("${gateway.app.jwt.header:Authorization}")
    private String header;

    @Value("${gateway.app.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${gateway.app.jwtExpirationMs}")
    private int expiration;

    @Value("${gateway.app.jwtSecret}")
    private String secret;

    public String getUri() {
        return Uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }
}
