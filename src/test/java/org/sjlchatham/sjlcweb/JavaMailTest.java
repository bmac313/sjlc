package org.sjlchatham.sjlcweb;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Date;
import java.util.Properties;

public class JavaMailTest {

    @Test
    public void mailSenderTest() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);

        //sender.setUsername("test@somedomain.com");
        //sender.setPassword("**********");

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("TEST");
        email.setSentDate(new Date());
        email.setText("This is a test of the Spring Java Mail system for St. John's Lutheran Church.");
        email.setTo("brendan.t.mcrae@gmail.com");
        email.setFrom("donotreply@sjlchatham.org");

        sender.send(email);
    }

}
