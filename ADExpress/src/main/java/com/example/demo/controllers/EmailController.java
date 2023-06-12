package com.example.demo.controllers;

import com.example.demo.services.Impl.EmailServiceImpl;
import com.sun.mail.iap.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/email")
public class EmailController {
    private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> sendEmail(@RequestParam(value = "toEmailAddress")String toEmailAddress,
                                            @RequestParam(value = "subject")String subject,
                                            @RequestParam(value = "message")String message){
        try {
            List<String> toEmailAddresses = new ArrayList<>();
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
            String response = emailService.sendEmailWithAttachment(emailad, "Mamencito ti", "Tova e sudurjanieto na imeila i bi trqbvalo da e prikachen i shibaniq pdf fail", "purchase_order.pdf");

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
}
