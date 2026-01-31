package org.example.onlinebankmanagementbackend.notification.service;

public interface EmailService {

    void sendTransactionEmail(
            String toEmail,
            String subject,
            String body
    );
}

