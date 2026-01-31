package org.example.onlinebankmanagementbackend.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    MailService(JavaMailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void sendTestMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Spring Boot Mail Test");
        message.setText("✅ Your Spring Boot Gmail configuration is working!");

        mailSender.send(message);
    }
}
