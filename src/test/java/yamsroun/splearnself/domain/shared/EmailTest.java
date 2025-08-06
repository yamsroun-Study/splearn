package yamsroun.splearnself.domain.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        Email email1 = new Email("jj@lim.com");
        Email email2 = new Email("jj@lim.com");

        assertThat(email1).isEqualTo(email2);
    }
}
