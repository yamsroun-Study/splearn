package yamsroun.splearnself.adapter.integration;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

import yamsroun.splearnself.application.member.required.EmailSender;
import yamsroun.splearnself.domain.shared.Email;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println(">>> DummyEmailSender: " + email);
    }
}
