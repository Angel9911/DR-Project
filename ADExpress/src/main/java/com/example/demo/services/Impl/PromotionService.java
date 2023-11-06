package com.example.demo.services.Impl;

import com.example.demo.private_lib.EmailMessage;
import com.example.demo.private_lib.jobs.promotion_jobs.PromotionEmailCron;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    private final PromotionEmailCron promotionEmailCron;

    @Autowired
    public PromotionService(PromotionEmailCron promotionEmailCron) {

        this.promotionEmailCron = promotionEmailCron;
    }

    public void schedulePromotionJob(String interval, String fromEmailAddress, String subject, String message) throws SchedulerException {

        EmailMessage emailMessage = EmailMessage.EmailMessageBuilder
                .get()
                .from(fromEmailAddress)
                .subject(subject)
                .message(message)
                .build();

        promotionEmailCron.setEmailMessage(emailMessage);

        promotionEmailCron.scheduleCron(interval,"promotion");
    }
}
