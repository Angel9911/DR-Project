package com.example.demo.services.Impl;

import com.example.demo.private_lib.EmailMessage;
import com.example.demo.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    protected Session session;
   // @Value("${spring.mail.username}")
   // private String sender;

    //@Autowired
    //private final JavaMailSender emailSender;

    public EmailServiceImpl() {
        final String username = "angelkrasimirov99@gmail.com";
        final String password = "";

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
    public String sendEmail(String fromEmailAddress, LinkedHashSet<String> toEmailAddresses, String subject, String message) {

        try {
            EmailMessage emailMessage = EmailMessage.EmailMessageBuilder
                    .get()
                    .from(fromEmailAddress)
                    .toEmailAddresses(toEmailAddresses)
                    .subject(subject)
                    .message(message)
                    .build();

            this.sendEmail(emailMessage);

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

            String messageText = "Hello"
                    + "\n You have request to reset your password"
                    + "\n Click the link below to change the password"
                    + "\n\n <p><a href=" + resetPassLink + "> Change my password</a><p>"
                    + "\n\n\n\n Ignore this email if you do remember your password or you have not made the request.";

            EmailMessage message = EmailMessage.EmailMessageBuilder
                    .get()
                    .from("angelkrasimirov99@gmail.com")
                    .to(toEmailAddress)
                    .subject("ADEXPRESS - Forgot Password")
                    .message(messageText)
                    .build();

            this.sendEmail(message);

            return "Mail sent successfully";

        }catch(Exception e){
            e.printStackTrace();
            return "Error while sending email";
        }
    }

    @Override
    public String sendEmailWithAttachment(String toEmailAddress, String subject, String message, String attachment) {
        try {
            String content = message+","+"\n\n\n <p style='color:red;text-decoration:underline;text-align:center'>tuka bi trqbvalo teksta da e centriran,cherven i podchertan </p>";

            EmailMessage emailMessage = EmailMessage.EmailMessageBuilder
                    .get()
                    .from("angelkrasimirov99@gmail.com")
                    .to(toEmailAddress)
                    .message(content)
                    .build();

            this.sendEmail(emailMessage);

            return "Mail sent successfully";
        }catch(MessagingException | UnsupportedEncodingException messagingException){
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



    private void sendEmail(EmailMessage emailMessage) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = new MimeMessage(session);
        String emailAddresses = "";

        if (!emailMessage.getToEmailAddresses().isEmpty()) {

            emailAddresses = String.join(", ", emailMessage.getToEmailAddresses());
        }

        if(emailMessage.getAttachmentFileName()!=null){

            message.setFrom(new InternetAddress(emailMessage.getFromEmailAddress()));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddresses));
            message.setSubject(emailMessage.getSubject());

            BodyPart mbody = new MimeBodyPart();
            mbody.setContent(emailMessage.getMessage(), "text/html");
            MimeBodyPart mbody1 = new MimeBodyPart();
            DataSource source = new FileDataSource("E:\\Programs\\SpringAngularProject\\angular7-springboot-crud-tutorial-master\\ADExpress\\src\\main\\resources\\images\\"+emailMessage.getAttachmentFileName());
            mbody1.setDataHandler(new DataHandler(source));
            mbody1.setFileName(emailMessage.getAttachmentFileName());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbody);
            multipart.addBodyPart(mbody1);
            message.setContent(multipart);
        }else if (!emailMessage.getSubject().equals("ADEXPRESS - Forgot Password")) {

            message.setFrom(new InternetAddress(emailMessage.getFromEmailAddress()));

                logger.debug("test if the recipients are correct" + emailAddresses);
                logger.debug("test if the email sender are correct" + emailMessage.getFromEmailAddress());

                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(emailAddresses)
                );

            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getMessage() + ","
                    + "\n\n Please do not spam my email!");

        } else{

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("angelkrasimirov99@gmail.com","ADEXPRESS SUPPORT");

            List<String> list = new ArrayList<>(emailMessage.getToEmailAddresses());

            if(!list.isEmpty()){

                String toEmailAddress = list.get(0);

                helper.setTo(toEmailAddress);
            }

            helper.setSubject(emailMessage.getSubject());
            helper.setText(emailMessage.getMessage(),true);
        }
        Transport.send(message);
    }
}
