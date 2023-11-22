package com.example.demo.controllers;

import com.example.demo.config.jobs.monitoring.ScheduleTasksCountMonitor;
import com.example.demo.exceptions.EmailException;
import com.example.demo.exceptions.EmailNotFoundException;
import com.example.demo.exceptions.FileNotFoundException;
import com.example.demo.exceptions.global.ObjectNotFoundException;
import com.example.demo.models.entity.Customer;
import com.example.demo.services.CustomerService;
import com.example.demo.services.Impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/email")
public class EmailController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    private final EmailServiceImpl emailService;

    private final CustomerService customerService;
    private final ScheduleTasksCountMonitor taskCountMonitor;
    @Autowired
    public EmailController(EmailServiceImpl emailService, CustomerService customerService, ScheduleTasksCountMonitor taskCountMonitor) {
        this.emailService = emailService;
        this.customerService = customerService;
        this.taskCountMonitor = taskCountMonitor;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> sendEmail(@RequestParam(value = "toEmailAddress")String toEmailAddress,
                                            @RequestParam(value = "subject")String subject,
                                            @RequestParam(value = "message")String message){
        try {


            LinkedHashSet<String> toEmailAddresses = new LinkedHashSet<>();
            toEmailAddresses.add("angelkrasimirov99@gmail.com");
            toEmailAddresses.add("dimitrovangel99@gmail.com");
            toEmailAddresses.add("singapur1@abv.bg");
            String response = emailService.sendEmail("angelkrasimirov99@gmail.com", toEmailAddresses, "TEST", "test test");
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(MailException mailException){
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/forgot/password", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> forgotPassword(@RequestParam(value = "toEmailAddress") String toEmailAddress) {

        Optional<Customer> customer = this.customerService.IsEmailExist(toEmailAddress);

        if(customer.isEmpty()){
            throw new ObjectNotFoundException(" with email"+toEmailAddress);
        }

        try {

            String response = emailService.forgotPassword(toEmailAddress);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(MailException mailException){
            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value="/send/attachment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> sendEmailWithAttachment(){
        try {

            String emailad = "singapur1@abv.bg";// "singapur1@abv.bg";
            System.out.println("running tasks: "+taskCountMonitor.getRunningTaskCount());
            String response = null;//emailService.sendEmailWithAttachment(emailad, "Subject", "Tova e sudurjanieto na imeila i bi trqbvalo da e prikachen i pdf fail", "purchase_order.pdf");

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(MailException mailException){

            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @RequestMapping(value = "/send/inline/attachment", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> sendEmailWithInlineAttachment(){
        try {

            String emailad = "singapur1@abv.bg";// "singapur1@abv.bg";
            String response = emailService.sendEmailWithInlineAttachment(emailad, "Mamencito ti", "Tova e sudurjanieto na imeila i bi trqbvalo da e prikachen i shibaniq fail dolu", "logo.png");

            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch(MailException mailException){

            LOG.error("Error while sending out email..{}", mailException.getStackTrace());
            return new ResponseEntity<>("Unable to send email", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // use these methods to test exception handler
    @RequestMapping(value = "/forgot/password/{email}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> testForgotPassword(@PathVariable(value = "email") String email) {
        System.out.println(email);
        throw new EmailNotFoundException("email "+email+" not found");
    }

    @RequestMapping(value="/send/attachment/{file}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> testEmailWithAttachment(@PathVariable(value = "file") String file) {
        throw new FileNotFoundException("file "+file+" not found");
    }

    @ExceptionHandler({EmailNotFoundException.class,FileNotFoundException.class})
    public ModelAndView handleEmailException(EmailException e){
        ModelAndView modelAndView = new ModelAndView("email-not-found");
        System.out.println(e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
