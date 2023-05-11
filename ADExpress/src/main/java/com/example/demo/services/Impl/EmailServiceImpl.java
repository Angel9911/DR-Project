package com.example.demo.services.Impl;

import org.hibernate.pretty.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService{
    protected Session session;
   // @Value("${spring.mail.username}")
   // private String sender;

    //@Autowired
    //private final JavaMailSender emailSender;

    public EmailServiceImpl() {
        final String username = "angelkrasimirov99@gmail.com";
        final String password = "vcpjqktuwpzmbipe";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    @Override
    public String sendEmail(String toEmailAddress, String subject, String message2) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("angelkrasimirov99@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("angelkrasimirov99@gmail.com, dimitrovangel99@gmail.com, singapur1@abv.bg")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Email sent via smtp protocol,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

          /*  SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(toEmailAddress);
            simpleMailMessage.setFrom("angelkrasimirov99@gmail.com");
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            emailSender.send(simpleMailMessage);  */
            return "Mail sent successfully";

        }catch(Exception e){
            e.printStackTrace();
            return "Error while sending email";
        }
    }

    @Override
    public String forgotPassword(String toEmailAddress) {

        try {
            String resetPassLink = "http://localhost:4200/customers/reset-password";
            MimeMessage mimeMessage = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("angelkrasimirov99@gmail.com","ADEXPRESS SUPPORT");
            helper.setTo(toEmailAddress);
            helper.setSubject("ADEXPRESS - Forgot Password");
            helper.setText("Hello"
                    + "\n You have request to reset your password"
                    + "\n Click the link below to change the password"
                    + "\n\n <p><a href=" + resetPassLink + "> Change my password</a><p>"
                    + "\n\n\n\n Ignore this email if you do remember your password or you have not made the request.",true);

            Transport.send(mimeMessage);

            return "Mail sent successfully";

        }catch(Exception e){
            e.printStackTrace();
            return "Error while sending email";
        }
    }

    @Override
    public String sendEmailWithAttachment(String toEmailAddress, String subject, String message, String attachment) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("angelkrasimirov99@gmail.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmailAddress));
            mimeMessage.setSubject(subject);

            BodyPart mbody = new MimeBodyPart();
            mbody.setContent(message+","+"\n\n\n <p style='color:red;text-decoration:underline;text-align:center'>tuka bi trqbvalo teksta da e centriran,cherven i podchertan </p>", "text/html");
           // mbody.setText(message);
            MimeBodyPart mbody1 = new MimeBodyPart();
            DataSource source = new FileDataSource("E:\\Programs\\SpringAngularProject\\angular7-springboot-crud-tutorial-master\\ADExpress\\src\\main\\resources\\images\\"+attachment);
            mbody1.setDataHandler(new DataHandler(source));
            mbody1.setFileName(attachment);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbody);
            multipart.addBodyPart(mbody1);
            mimeMessage.setContent(multipart);

            //FileSystemResource fileSystemResource = new FileSystemResource(ResourceUtils.getFile("images/"+attachment));
            //MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
           // messageHelper.addAttachment("Test file attachment",fileSystemResource);

            Transport.send(mimeMessage);
            return "Mail sent successfully";
        }catch(MessagingException messagingException){
            messagingException.printStackTrace();
            return "Error while sending email";
        }
    }

    @Override
    public String sendEmailWithInlineAttachment(String toEmailAddress, String subject, String message, String attachment) {

        //inline send
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("angelkrasimirov99@gmail.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmailAddress));
            mimeMessage.setSubject(subject);
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setText("<p style='color:red;text-decoration:underline;'>"+message+"</p>"+"\n\n<html><body><img src='cid:identifier1234'></body></html>", true);

            FileSystemResource res = new FileSystemResource(new File("E:\\Programs\\SpringAngularProject\\angular7-springboot-crud-tutorial-master\\ADExpress\\src\\main\\resources\\images\\"+attachment));
            helper.addInline("identifier1234", res);


            Transport.send(mimeMessage);
            return "Mail sent successfully";
        }catch(MessagingException messagingException){
            messagingException.printStackTrace();
            return "Error while sending email";
        }
    }
}
