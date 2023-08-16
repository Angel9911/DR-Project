package com.example.demo.private_lib.jobs;

import com.example.demo.repositories.CustomerRepository;
import com.example.demo.services.Impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailCronJob {
    @Value("${cron.sendPromotion.interval}")
    private long cronInterval; // default value is 1 day

    private String fromEmailAddress;
    private String subject;
    private String message;
   // @Autowired
    private final EmailServiceImpl emailService;
   // @Autowired
    private final CustomerRepository customerRepository;


    public EmailCronJob(EmailServiceImpl emailService, CustomerRepository customerRepository) {
        this.emailService = emailService;
        this.customerRepository = customerRepository;
    }

    public void setCronInterval(String cronInterval) {
        this.cronInterval = Long.parseLong(cronInterval);
    }

    public long getCronInterval() {
        return cronInterval;
    }

    public void setFromEmailAddress(String fromEmailAddress) {
        this.fromEmailAddress = fromEmailAddress;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Scheduled(fixedDelayString = "#{@emailCronJob.getCronInterval()}")
    public void sendPromotiontoUsers(){
       /* try {
            System.out.println(getCronInterval());
            if(StringUtils.isBlank(fromEmailAddress) || StringUtils.isBlank(subject) || StringUtils.isBlank(message)){
                throw new IllegalArgumentException("Invalid input parameters");
            }
            List<Customer> getAllCustomers = this.customerRepository.findAll();
            List<String> toEmailAddresses = new ArrayList<>();

            for (Customer customer : getAllCustomers){
                if(customer.getEmail()!=null) {
                    toEmailAddresses.add(customer.getEmail());
                }
            }
            System.out.println(fromEmailAddress);
            System.out.println(toEmailAddresses);
            System.out.println(subject+message);
            this.emailService.sendEmail(fromEmailAddress,toEmailAddresses,subject,message);

        }catch (Exception e){
            e.printStackTrace();

            throw new RuntimeException("Failed to send promotion emails");
        } */
    }
}
