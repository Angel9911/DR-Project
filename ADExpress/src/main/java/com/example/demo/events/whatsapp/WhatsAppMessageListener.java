package com.example.demo.events.whatsapp;

import com.example.demo.events.NotificationMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WhatsAppMessageListener {
    Logger logger = LoggerFactory.getLogger(WhatsAppMessageListener.class);

    @Value("${adexpress.whatsapp.api.url}")
    private String apiUrl;

    @Value("${adexpress.whatsapp.auth.token}")
    private String authToken;

    @EventListener(NotificationMessageEvent.class)
    public void onWhatsAppMessageEvent(NotificationMessageEvent whatsAppMessageEvent){

        try {

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(apiUrl))
                    .header("Authorization","Bearer "+authToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \""+whatsAppMessageEvent.getPhoneNumber()+"\", \"type\": \"template\", \"template\": { \"name\": \""+whatsAppMessageEvent.getTemplateName()+"\", \"language\": { \"code\": \"bg\" } } }"))
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.info(" received event in WhatsApp Listener: {}", response.body());

        }catch (URISyntaxException | IOException | InterruptedException e){
            logger.error(" error in WhatsApp Listener: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
