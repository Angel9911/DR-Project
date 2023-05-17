package com.shared.library.config;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JwtConfig {
    private static final String CONFIG_FILE = "config.properties";
    private Properties properties;

    private String uri;

    private String header;

    private String prefix;

    private int expiration;

    private String secret;

    public JwtConfig(Properties properties) {
        this.properties = properties;

        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE)) {

            properties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JwtConfig() {
    }

    public String getUri() {

        return getValue("routeAuth");
    }

    public String getHeader() {
        return getValue("jwtHeader");
    }

    public String getPrefix() {
        return getValue("jwtPrefix");
    }

    public int getExpiration() {

        return Integer.parseInt(getValue("jwtExpirationMs"));
    }

    public String getSecret() {
        return getValue("jwtSecret");
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
