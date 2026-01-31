package org.example.onlinebankmanagementbackend.otp;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    private final JavaMailSender mailSender;

    OtpService(OtpRepository otpRepository, JavaMailSender mailSender)
    {
        this.otpRepository = otpRepository;
        this.mailSender = mailSender;
    }

    private static final int MAX_REQUESTS = 3;
    private static final int WINDOW_MINUTES = 10;

    public void sendOtp(String email)
    {

        LocalDateTime now = LocalDateTime.now();

        Otp otpEntity = otpRepository.findByEmail(email)
                .orElse(new Otp());

        if (otpEntity.getLastRequestTime() != null)
        {
            long minutesPassed = Duration.between(
                    otpEntity.getLastRequestTime(), now
            ).toMinutes();

            if (minutesPassed < WINDOW_MINUTES &&
                    otpEntity.getRequestCount() >= MAX_REQUESTS)
            {

                throw new RuntimeException(
                        "Too many OTP requests. Try again after "
                                + (WINDOW_MINUTES - minutesPassed) + " minutes."
                );
            }

            if (minutesPassed >= WINDOW_MINUTES)
            {
                otpEntity.setRequestCount(0);
            }
        }

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(now.plusMinutes(5));
        otpEntity.setLastRequestTime(now);
        otpEntity.setRequestCount(otpEntity.getRequestCount() + 1);

        otpRepository.save(otpEntity);

        sendOtpMailAsync(email, otp);
    }

    @Async
    public void sendOtpMailAsync(String email, String otp)
    {
        sendOtpMail(email, otp);
    }

    private void sendOtpMail(String email, String otp)
    {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Your OTP for Online Bank Management");

            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body {
                        background-color: #f4f6f8;
                        font-family: Arial, sans-serif;
                        padding: 20px;
                    }
                    .container {
                        max-width: 500px;
                        margin: auto;
                        background: #ffffff;
                        border-radius: 8px;
                        padding: 25px;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                        text-align: center;
                    }
                    .logo {
                        width: 120px;
                        margin-bottom: 20px;
                    }
                    .otp {
                        font-size: 28px;
                        letter-spacing: 6px;
                        font-weight: bold;
                        color: #2c3e50;
                        background: #f1f5f9;
                        padding: 12px 20px;
                        display: inline-block;
                        border-radius: 6px;
                        margin: 20px 0;
                    }
                    .footer {
                        font-size: 12px;
                        color: #777;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <img src="https://yourdomain.com/logo.png" class="logo" alt="Bank Logo"/>

                    <h2>OTP Verification</h2>
                    <p>Use the OTP below to complete your verification:</p>

                    <div class="otp">%s</div>

                    <p>This OTP is valid for <b>5 minutes</b>.</p>

                    <div class="footer">
                        © 2026 Online Bank Management. All rights reserved.
                    </div>
                </div>
            </body>
            </html>
        """.formatted(otp);

            helper.setText(htmlContent, true);
            helper.setFrom("noreply@onlinebank.com");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }


    public boolean verifyOtp(String email, String userOtp) {

        Optional<Otp> otpOptional = otpRepository.findByEmail(email);

        if (otpOptional.isEmpty()) return false;

        Otp otp = otpOptional.get();

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otp);
            return false;
        }

        if (!otp.getOtp().equals(userOtp)) return false;

        otpRepository.delete(otp); // One-time use
        return true;
    }

}
