package com.example.demo.events;

import org.springframework.context.ApplicationEvent;

public class NotificationMessageEvent extends ApplicationEvent {

    private int shipmentId;

    private String phoneNumber;

    private String templateName;

    public NotificationMessageEvent(Object source, int shipmentId, String phoneNumber, String templateName) {
        super(source);
        this.shipmentId = shipmentId;
        this.phoneNumber = phoneNumber;
        this.templateName = templateName;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
