package yamsroun.splearnself.domain;

import jakarta.persistence.Entity;

import org.hibernate.annotations.NaturalId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

//XML은 Annotation 설정을 Override한다.
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class Member extends AbstractEntity {

    @NaturalId
    private Email email;

    private String nickname;
    private String passwordHash;
    private MemberStatus status;

    private MemberDetail detail;

    
    public static Member register(MemberRegisterRequest request, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.email = new Email(request.email());
        member.nickname = requireNonNull(request.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(request.password()));
        member.status = MemberStatus.PENDING;
        return member;
    }

    public void activate() {
        state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;
    }

    public void deactivate() {
        state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");
        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String secret, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(secret, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
