package yamsroun.splearnself.domain.member;

import jakarta.persistence.Entity;

import org.hibernate.annotations.NaturalId;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import yamsroun.splearnself.domain.AbstractEntity;
import yamsroun.splearnself.domain.shared.Email;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

//XML은 Annotation 설정을 Override한다.
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true, exclude = {"detail"})
public class Member extends AbstractEntity {

    @NaturalId
    private Email email;

    private String nickname;
    private String passwordHash;
    private MemberStatus status;

    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberDetail detail;


    public static Member register(MemberRegisterRequest request, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.email = new Email(request.email());
        member.nickname = requireNonNull(request.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(request.password()));
        member.status = MemberStatus.PENDING;

        member.detail = MemberDetail.create();

        return member;
    }

    public void activate() {
        state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(this.status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");
        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String secret, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(secret, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        state(this.status == MemberStatus.ACTIVE, "등록완료 상태가 아니면 정보를 수정할 수 없습니다.");
        this.nickname = Objects.requireNonNull(updateRequest.nickname());
        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
