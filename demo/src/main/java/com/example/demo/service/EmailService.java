package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.request.EmailRequest;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired 
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendMail(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setSubject(emailRequest.getSubject());
            mimeMessageHelper.setText(emailRequest.getContent(), true);
            mimeMessageHelper.setTo(emailRequest.getToEmail());

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException("Error sending email");
        }
    }
}