package com.example.demo.events.viber;

import com.example.demo.events.NotificationMessageEvent;
import com.example.demo.events.whatsapp.WhatsAppMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ViberMessageListener {
    Logger logger = LoggerFactory.getLogger(WhatsAppMessageListener.class);

    @Value("${adexpress.viber.auth.token}")
    private String viberAuthToken;

    @EventListener(NotificationMessageEvent.class)
    public void onViberMessageEvent(NotificationMessageEvent viberMessageEvent){

        logger.info("received event in Viber Listener: ");

        //TODO: implement sending a notification message to certain user on viber when the user received his package.
    }
}
