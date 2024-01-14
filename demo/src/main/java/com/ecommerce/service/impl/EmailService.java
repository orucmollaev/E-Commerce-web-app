package com.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    void sendOrderNotification(String sellerEmail, String productName, int requestedQuantity) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sellerEmail);
        message.setSubject("You have new order! ");
        message.setText("Hello." + "\nA new order has been placed for your product '" + productName +
                "' with a quantity of " + requestedQuantity + "." + "\nPlease be informed!");
        javaMailSender.send(message);
    }
}