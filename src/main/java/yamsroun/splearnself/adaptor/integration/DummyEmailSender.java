package yamsroun.splearnself.adaptor.integration;

import org.springframework.stereotype.Component;

import yamsroun.splearnself.application.required.EmailSender;
import yamsroun.splearnself.domain.Email;

@Component
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println(">>> DummyEmailSender: " + email);
    }
}
