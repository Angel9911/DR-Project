package com.example.demo.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NotificationMessagePublisher {
    private final ApplicationEventPublisher publisher;

    public NotificationMessagePublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishMessage(int shipmentId, String phone, String message){

        NotificationMessageEvent messageEvent = new NotificationMessageEvent(this, shipmentId, phone, message);

        publisher.publishEvent(messageEvent);
    }
}
