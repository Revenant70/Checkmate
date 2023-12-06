package com.todo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAuthService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationCode(String toEmail, String confirmationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Confirmation Code");
        message.setText("Your confirmation code is: " + confirmationCode);

        javaMailSender.send(message);
    }


}