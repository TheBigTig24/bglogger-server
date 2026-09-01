package com.example.bglogger.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendVerificationEmail(String to, String subject, String token) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);

            // String frontendUrl = "http://localhost:3000/verify?token=" + token;
            String frontendUrl = "http://localhost:8080/api/v1/users/verify?token=" + token;
            
            String htmlMessage = "<html>"
                + "<body style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to Your Board Game Logger!</h2>"
                + "<p style=\"font-size: 16px;\">Click <a href=\"" + frontendUrl + "\">here</a> to verify your account.</p>"
                + "</div>"
                + "</body>"
                + "</html>";

            helper.setText(htmlMessage, true);

            emailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
