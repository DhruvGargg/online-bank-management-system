package org.example.onlinebankmanagementbackend.mail.controller;

import org.example.onlinebankmanagementbackend.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MailTestController {

    private final MailService mailService;

    MailTestController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/mail")
    public String sendMail() {
        mailService.sendTestMail("gargdhruv180@gmail.com");
        return "📧 Mail sent successfully!";
    }
}
