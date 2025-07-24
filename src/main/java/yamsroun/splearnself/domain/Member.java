package yamsroun.splearnself.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@ToString
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Member {

    private Email email;
    private String nickname;
    private String passwordHash;
    private MemberStatus status;

    public static Member register(MemberRegisterRequest request, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.email = new Email(request.email());
        member.nickname = requireNonNull(request.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(request.password()));
        member.status = MemberStatus.PENDING;
        return member;
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
        this.nickname = requireNonNull(nickname);
    }

    void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
