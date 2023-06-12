package com.example.demo.services.Impl;

import java.util.List;

public interface EmailService {
    String sendEmail(String fromEmailAddress, List<String> toEmailAddresses, String subject, String message);
    String forgotPassword(String toEmailAddress);
    String sendEmailWithAttachment(String toEmailAddress, String subject, String message, String attachment);
    String sendEmailWithInlineAttachment(String toEmailAddress, String subject, String message, String attachment);
}
