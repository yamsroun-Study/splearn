package yamsroun.splearnself.adaptor.integration;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import yamsroun.splearnself.domain.Email;

import static org.assertj.core.api.Assertions.assertThat;


class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("jj@lim.com"), "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo(">>> DummyEmailSender: Email[address=jj@lim.com]");
    }

}
