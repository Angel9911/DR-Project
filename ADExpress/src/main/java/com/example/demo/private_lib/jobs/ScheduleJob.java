package com.example.demo.private_lib.jobs;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface ScheduleJob {

    void scheduleCron(String interval, String identityName) throws SchedulerException;
}
