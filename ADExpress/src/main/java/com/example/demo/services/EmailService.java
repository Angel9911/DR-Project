package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;

@Service
public interface EmailService {
    String sendEmail(String fromEmailAddress, LinkedHashSet<String> toEmailAddresses, String subject, String message);
    String forgotPassword(String toEmailAddress);
    String sendEmailWithAttachment(String toEmailAddress, String subject, String message, String attachment);
    String sendEmailWithInlineAttachment(String toEmailAddress, String subject, String message, String attachment);
}
