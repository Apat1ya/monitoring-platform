package io.github.apat1ya.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${app.email.confirm-url}")
    private String confirmUrl;

    public void sendEmailChangeConfirmation(String email, String token) {
        String link = confirmUrl + "?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirm email change");
        mailMessage.setText(
                "To confirm your email change, follow the link:\n" + link
        );

        mailSender.send(mailMessage);
    }
}
