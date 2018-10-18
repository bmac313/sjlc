package org.sjlchatham.sjlcweb.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// Note: this feature will be tested and implemented after the site and email server have been set up, as there
// is not a reliable way of testing it at the moment.
@Configuration
public class EmailConfiguration {

    @Bean(name = "mailSender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // Email login credentials. Replace the below placeholder with the site email credentials in production.
        //mailSender.setUsername("someemail@sjlchatham.org");
        //mailSender.setPassword("********");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
