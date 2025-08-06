package yamsroun.splearnself.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        assertThatCode(() -> {
            new Profile("jjlim");
            new Profile("jjlim123");
            new Profile("12345");
        }).doesNotThrowAnyException();
    }

    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile(null)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("toloooooooooooong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("upperCase")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        String profileAddress = "jjlim";
        Profile profile = new Profile(profileAddress);
        assertThat(profile.url()).isEqualTo("@" + profileAddress);
    }
}
