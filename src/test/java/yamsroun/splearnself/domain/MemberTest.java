package yamsroun.splearnself.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncoder() {

            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        member = Member.create("jj@lim.com", "JJ", "secret", passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    //@Test
    //void constructorNullCheck() {
    //    assertThatThrownBy(() -> Member.create(null, "JJ", "secret", passwordEncoder))
    //        .isInstanceOf(NullPointerException.class);
    //}

    @Test
    void activate() {
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();
        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();
        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        boolean verified = member.verifyPassword("secret", passwordEncoder);
        assertThat(verified).isTrue();
        boolean verified2 = member.verifyPassword("hello", passwordEncoder);
        assertThat(verified2).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("JJ");

        member.changeNickname("yamsroun");
        assertThat(member.getNickname()).isEqualTo("yamsroun");
    }

    @Test
    void changePassword() {
        member.changePassword("topsecret", passwordEncoder);
        assertThat(member.verifyPassword("topsecret", passwordEncoder)).isTrue();
    }
}
