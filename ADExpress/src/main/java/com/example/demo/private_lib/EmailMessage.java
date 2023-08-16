package com.example.demo.private_lib;

import java.util.LinkedHashSet;

public class EmailMessage {

    public static class EmailMessageBuilder{

        private EmailMessage emailMessage;

        private EmailMessageBuilder() {
            this.emailMessage = new EmailMessage();
        }

        public static EmailMessageBuilder get(){
            return new EmailMessageBuilder();
        }

        public EmailMessageBuilder from(String fromEmailAddress){
            this.emailMessage.setFromEmailAddress(fromEmailAddress);
            return this;
        }

        public EmailMessageBuilder to(String toEmailAddress){
            this.emailMessage.setToAddress(toEmailAddress);
            return this;
        }

        public EmailMessageBuilder toEmailAddresses(LinkedHashSet<String> toEmailAddresses){
            this.emailMessage.setToEmailAddresses(toEmailAddresses);
            return this;
        }

        public EmailMessageBuilder subject(String subject){
            this.emailMessage.setSubject(subject);
            return this;
        }

        public EmailMessageBuilder message(String message){
            this.emailMessage.setMessage(message);
            return this;
        }

        public EmailMessageBuilder attach(String fileName){
            this.emailMessage.setAttachmentFileName(fileName);
            return this;
        }
        public EmailMessage build(){
            return this.emailMessage;
        }

    }

    private String fromEmailAddress;
    private LinkedHashSet<String> toEmailAddresses;
    private String subject;
    private String message;
    private String attachmentFileName;



    private void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    private void setToEmailAddresses(LinkedHashSet<String> toEmailAddresses) {
        this.toEmailAddresses = toEmailAddresses;
    }
    private void setToAddress(String toEmailAddress){
        this.toEmailAddresses.add(toEmailAddress);
    }


    private void setSubject(String subject) {
        this.subject = subject;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setAttachmentFileName(String attachmentFileName) {
        this.attachmentFileName = attachmentFileName;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }

    public LinkedHashSet<String> getToEmailAddresses() {
        return toEmailAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getAttachmentFileName() {
        return attachmentFileName;
    }
}
