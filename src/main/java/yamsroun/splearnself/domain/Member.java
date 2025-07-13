package yamsroun.splearnself.domain;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

import static org.springframework.util.Assert.state;

@ToString
@Getter
public class Member {

    private String email;
    private String nickname;
    private String passwordHash;
    private MemberStatus status;

    private Member(String email, String nickname, String passwordHash) {
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.status = MemberStatus.PENDING;
    }

    public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
        return new Member(email, nickname, passwordEncoder.encode(password));
    }

    void activate() {
        state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;
    }

    void deactivate() {
        state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");
        this.status = MemberStatus.DEACTIVATED;
    }

    boolean verifyPassword(String secret, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(secret, this.passwordHash);
    }

    void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }
}
