package yamsroun.splearnself;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import yamsroun.splearnself.application.member.required.EmailSender;
import yamsroun.splearnself.domain.member.MemberFixture;
import yamsroun.splearnself.domain.member.PasswordEncoder;

@TestConfiguration
public class SplearnTestConfig {

    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println(">>> Sending email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
