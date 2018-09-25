package com.app.security.events;

import com.app.model.security.User;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationEventData> {

    private UserService userService;
    private JavaMailSender javaMailSender;

    public RegistrationListener(UserService userService, @Qualifier("javaMailSender") JavaMailSender javaMailSender) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void onApplicationEvent(RegistrationEventData data) {
        confirmRegistration(data);
    }

    private void confirmRegistration(RegistrationEventData data) {
        User user = data.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(token,user);

        String recipientAddress = user.getEmail();
        String subject = "REGISTRATION CONFIRM";
        String confirmURL = data.getUrl() + "security/registrationConfirm?token=" + token;
        String message = "CLICK TO ACTIVATE: " + confirmURL;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        javaMailSender.send(simpleMailMessage);
    }
}
