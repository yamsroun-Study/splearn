package yamsroun.splearnself.domain.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

import org.springframework.util.Assert;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import yamsroun.splearnself.domain.AbstractEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class MemberDetail extends AbstractEntity {

    @Embedded
    private Profile profile;

    private String introduction;
    private LocalDateTime registeredAt;
    private LocalDateTime activatedAt;
    private LocalDateTime deactivatedAt;


    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void activate() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt은 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        Assert.isTrue(deactivatedAt == null, "이미 deactivatedAt은 설정되었습니다.");
        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
