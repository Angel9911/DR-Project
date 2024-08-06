package com.example.demo.config;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PayPalConfig {
    private final String baseUrl = "http://localhost:4200";
    private final String success_url = "/customers/home";
    private final String cancel_url = "";

    @Bean
    public APIContext getPayPalApiInstance(@Value("${adexpress.paypal.clientId}") String clientId
            , @Value("${adexpress.paypal.clientSecret}") String secretId
            , @Value("${adexpress.paypal.mode}") String environmentMode){

        // works like conduit between paypal and our application and used for authentication.
        return new APIContext(clientId,secretId,environmentMode);
    }

    @Bean
    protected RedirectUrls getRedirectsUrl() {
        RedirectUrls redirectUrls = new RedirectUrls();

        redirectUrls.setCancelUrl(baseUrl+cancel_url);
        redirectUrls.setReturnUrl(baseUrl+success_url);

        return redirectUrls;
    }
}
