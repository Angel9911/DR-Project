package com.example.demo.services.Impl;

public interface EmailService {
    String sendEmail(String toEmailAddress, String subject, String message);
    String forgotPassword(String toEmailAddress);
    String sendEmailWithAttachment(String toEmailAddress, String subject, String message, String attachment);
    String sendEmailWithInlineAttachment(String toEmailAddress, String subject, String message, String attachment);
}
