package yamsroun.splearnself.adaptor.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder passwordEncoder = new SecurePasswordEncoder();
        String passwordHash = passwordEncoder.encode("secret");
        assertThat(passwordEncoder.matches("secret", passwordHash)).isTrue();
        assertThat(passwordEncoder.matches("wrong", passwordHash)).isFalse();
    }

}
