package com.stomat.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Anton Chelyadin.
 * @since 16.09.18.
 */
@Service
public class EmailSender {

    @Autowired
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final JavaMailSender mailSender;

    @Value("spring.mail.username")
    String emailFrom;

    public void send(String emailTo, String subject, String message) {

        var mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

}
