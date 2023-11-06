package com.example.demo.private_lib.jobs.promotion_jobs;

import com.example.demo.private_lib.EmailMessage;
import com.example.demo.private_lib.jobs.ScheduleJob;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

public interface EmailScheduleJob extends ScheduleJob {
    void rescheduleCron(String interval, String oldTriggerIdentity) throws SchedulerException;
    void setEmailMessage(EmailMessage emailMessage);
}
