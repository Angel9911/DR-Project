package com.example.demo.private_lib.jobs;

import com.example.demo.config.jobs.monitoring.ScheduleTasksCountMonitor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestCron2 {


    @Scheduled(fixedRate = 1000)
    public void execute() throws InterruptedException {
        Thread.sleep(2000);

        //System.out.println("test2");
    }

}
