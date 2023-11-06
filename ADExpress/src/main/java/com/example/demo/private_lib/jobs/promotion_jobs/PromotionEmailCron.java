package com.example.demo.private_lib.jobs.promotion_jobs;

import com.example.demo.config.jobs.monitoring.ScheduleTasksCountMonitor;
import com.example.demo.models.entity.Customer;
import com.example.demo.private_lib.EmailMessage;
import com.example.demo.services.CustomerService;
import com.example.demo.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Slf4j
@Component
public class PromotionEmailCron implements Job,EmailScheduleJob {
    private static final Logger logger = LoggerFactory.getLogger(PromotionEmailCron.class);

    private final Scheduler scheduler;
    private EmailMessage emailMessage;

    private CustomerService customerService;
    private EmailService emailService;
    private final ScheduleTasksCountMonitor taskCountMonitor;

    public PromotionEmailCron(Scheduler scheduler,ScheduleTasksCountMonitor taskCountMonitor) {
        this.scheduler = scheduler;
        this.taskCountMonitor = taskCountMonitor;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String getFromEmail = dataMap.getString("from");
        String getSubject = dataMap.getString("subject");
        String getMessage = dataMap.getString("message");

        try {

            if(StringUtils.isBlank(getFromEmail) || StringUtils.isBlank(getSubject) || StringUtils.isBlank(getMessage)){
                logger.error("There are empty values");
                throw new IllegalArgumentException("Invalid input parameters");
            }
            List<Customer> getAllCustomers = customerService.getAllCustomers();
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

    @Override
    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    @Override
    public void scheduleCron(String interval, String identityName) throws SchedulerException {

        scheduler.scheduleJob(getJobDetail(identityName, this.emailMessage), getTrigger(identityName, interval));
        taskCountMonitor.incrementTaskCount();
    }

    /**
     * Schedule existing cron with a new cron interval
     */
    @Override
    public void rescheduleCron(String interval, String oldTriggerIdentity) throws SchedulerException {
        String cronExpression = "0 */"+interval+" * * * ?";

        TriggerKey oldtriggerKey = TriggerKey.triggerKey("promotionTrigger");

        CronTrigger newTrigger = TriggerBuilder.newTrigger()
                .withIdentity("promotionTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.rescheduleJob(oldtriggerKey,newTrigger);
    }

    private JobDetail getJobDetail(String identityName, EmailMessage emailMessage) {

        if(emailMessage == null){

            logger.error("The email does not exist");
            throw new IllegalStateException("The email does not exist");
        }

        if(emailMessage.getFromEmailAddress().isEmpty() || emailMessage.getSubject().isEmpty() || emailMessage.getMessage().isEmpty()){

            logger.error("There is empty email attribute. The email attributes from: {} subject: {} message: {}"
                    ,emailMessage.getMessage(),emailMessage.getSubject(),emailMessage.getMessage());

            throw new IllegalStateException("The email attributes can not be empty");
        }

        return JobBuilder.newJob(PromotionEmailCron.class)
                .withIdentity(identityName+"Job")
                .usingJobData("from", emailMessage.getFromEmailAddress())
                .usingJobData("subject", emailMessage.getSubject())
                .usingJobData("message", emailMessage.getMessage())
                .build();
    }

    private Trigger getTrigger(String identityName, String interval) {
        String cronExpression = "0 */"+interval+" * * * ?";

        return TriggerBuilder.newTrigger()
                .withIdentity(identityName+"Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }
}
