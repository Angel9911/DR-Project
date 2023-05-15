package com.example.commonservice.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter        // lombok will create getters auto.
@ToString
public class JwtConfig {

    @Value("${common.app.route.auth}")
    private String Uri;

    @Value("${common.app.jwt.header:Authorization}")
    private String header;

    @Value("${common.app.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${common.app.jwtExpirationMs}")
    private int expiration;

    @Value("${common.app.jwtSecret}")
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
