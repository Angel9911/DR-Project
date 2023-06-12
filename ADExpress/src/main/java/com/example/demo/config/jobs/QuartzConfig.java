package com.example.demo.config.jobs;

import com.example.demo.private_lib.jobs.PromotionCron;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import javax.swing.*;
import java.util.Properties;

/**
 * This class contains main configuration of Quartz. Here we access spring application and retrieves Scheduler instance
 * to schedule jobs and manage the Quartz Scheduler.
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private QuartzProperties quartzProperties;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory schedulerFactory(){

        return new SpringBeanJobFactory(){

            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                final Object job = super.createJobInstance(bundle);

                applicationContext
                        .getAutowireCapableBeanFactory()
                        .autowireBean(job);

                return job;
            }
        };

    }
    @Bean
    public SchedulerFactoryBean createSchedulerFactory() {
        SchedulerFactoryBean schedulerFactory
                = new SchedulerFactoryBean();
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        SpringBeanJobFactory springBeanJobFactory = new SpringBeanJobFactory();
        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(springBeanJobFactory);

        return schedulerFactory;

          /* SchedulerJobFactory schedulerJobFactory = new SchedulerJobFactory();
        schedulerJobFactory.setApplicationContext(applicationContext);

        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setJobFactory(schedulerJobFactory);
        return schedulerFactoryBean;*/
        //schedulerFactoryBean.setApplicationContext(applicationContext);
    }

   /* @Bean
    public SimpleTriggerFactoryBean
    createSimpleTriggerFactoryBean(JobDetail jobDetail)
    {
        SimpleTriggerFactoryBean simpleTriggerFactory
                = new SimpleTriggerFactoryBean();

        simpleTriggerFactory.setJobDetail(jobDetail);
        simpleTriggerFactory.setStartDelay(0);
        simpleTriggerFactory.setRepeatInterval(5000);
        simpleTriggerFactory.setRepeatCount(10);
        return simpleTriggerFactory;
    } */
    @Bean
    public JobDetailFactoryBean createJobDetailFactoryBean(){

        JobDetailFactoryBean jobDetailFactory
                = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(PromotionCron.class);
        return jobDetailFactory;
    }

    @Bean
    public Scheduler scheduler(){
        return createSchedulerFactory().getScheduler();
    }
}
