package yamsroun.splearnself.domain;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true)
public class MemberDetail extends AbstractEntity {

    private String profile;
    private String introduction;
    private LocalDateTime registeredAt;
    private LocalDateTime activatedAt;
    private LocalDateTime deactivatedAt;
}
