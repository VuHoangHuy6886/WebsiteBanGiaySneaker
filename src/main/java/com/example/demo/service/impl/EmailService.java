package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vuhoanghuy123321@gmail.com"); // Địa chỉ email người gửi
        message.setTo(to); // Địa chỉ email người nhận
        message.setSubject(subject); // Tiêu đề email
        message.setText(text); // Nội dung email

        mailSender.send(message);
    }

}
