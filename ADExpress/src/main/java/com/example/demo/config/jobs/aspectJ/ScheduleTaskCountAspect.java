package com.example.demo.config.jobs.aspectJ;

import com.example.demo.config.jobs.monitoring.ScheduleTasksCountMonitor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ScheduleTaskCountAspect {

        private final ScheduleTasksCountMonitor taskCountMonitor;

        public ScheduleTaskCountAspect(ScheduleTasksCountMonitor taskCountMonitor) {
            this.taskCountMonitor = taskCountMonitor;
        }

        @Before("@annotation(org.springframework.scheduling.annotation.Scheduled)")
        public void beforeScheduledTask() {
            taskCountMonitor.incrementTaskCount();
        }

        @After("@annotation(org.springframework.scheduling.annotation.Scheduled)")
        public void afterScheduledTask() {
            taskCountMonitor.decrementTaskCount();
        }
}
