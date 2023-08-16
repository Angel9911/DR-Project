package com.example.demo.private_lib.jobs;

import com.example.demo.models.entity.Customer;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.Impl.CustomerServiceImpl;
import com.example.demo.services.Impl.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@Component
public class PromotionCron implements Job {
    private static final Logger logger = LoggerFactory.getLogger(PromotionCron.class);
   // @Autowired
    private final Scheduler scheduler;
   // @Autowired
    private final EmailServiceImpl emailService;
   // @Autowired
    private CustomerServiceImpl customerService;
    //@Autowired
    final CustomerRepository customerRepository;

    @Autowired
    public PromotionCron(Scheduler scheduler, EmailServiceImpl emailService, CustomerRepository customerRepository) {
        this.scheduler = scheduler;
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }

    @Override
    public void execute(final JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String getFromEmail = dataMap.getString("from");
        String getSubject = dataMap.getString("subject");
        String getMessage = dataMap.getString("message");

        try {

            if(StringUtils.isBlank(getFromEmail) || StringUtils.isBlank(getSubject) || StringUtils.isBlank(getMessage)){
                logger.error("There are empty values");
                throw new IllegalArgumentException("Invalid input parameters");
            }
            List<Customer> getAllCustomers = customerRepository.findAll();
            LinkedHashSet<String> toEmailAddresses = new LinkedHashSet<>();

            for (Customer customer : getAllCustomers){
                if(customer.getEmail()!=null) {
                    toEmailAddresses.add(customer.getEmail());
                }
            }
            logger.info("email addresses which should received an emails are: "+toEmailAddresses);
            logger.info("email content"+getMessage);
            emailService.sendEmail(getFromEmail,toEmailAddresses,getSubject,getMessage);

        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();

            throw new RuntimeException("Failed to send promotion emails");
        }
    }
    @Transactional
    public void schedulePromotionJob(String interval, String fromEmailAddress, String subject, String message) throws SchedulerException {

        String cronExpression = "0 */"+interval+" * * * ?";
        System.out.println(interval+fromEmailAddress+subject+message);
        JobDetail jobDetail = JobBuilder.newJob(PromotionCron.class)
                .withIdentity("promotionJob")
                .usingJobData("from", fromEmailAddress)
                .usingJobData("subject", subject)
                .usingJobData("message", message)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("promotionTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail,trigger);
    }

    /**
     * Schedule existing cron with a new cron interval
     */
    public void reschedulePromotionJob(String interval) throws SchedulerException {
        String cronExpression = "0 */"+interval+" * * * ?";

        TriggerKey oldtriggerKey = TriggerKey.triggerKey("promotionTrigger");

        CronTrigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity("promotionTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.rescheduleJob(oldtriggerKey,newTrigger);
    }
}
