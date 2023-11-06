package com.example.demo.config.jobs.monitoring;

import com.google.api.gax.rpc.AsyncTaskException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduleTasksCountMonitor {
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final AtomicInteger runningTaskCount = new AtomicInteger(0);

    public ScheduleTasksCountMonitor(@Qualifier("applicationTaskExecutor") AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    public AtomicInteger getRunningTaskCount() {
        return runningTaskCount;
    }

    public void incrementTaskCount(){
        int i = runningTaskCount.incrementAndGet();
        runningTaskCount.set(i);
    }
    public void decrementTaskCount(){
        int i = runningTaskCount.decrementAndGet();
        runningTaskCount.set(i);
    }
}
