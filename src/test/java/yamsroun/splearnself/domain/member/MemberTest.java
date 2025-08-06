package yamsroun.splearnself.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static yamsroun.splearnself.domain.member.MemberFixture.createMemberRegisterRequest;
import static yamsroun.splearnself.domain.member.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
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
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
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
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
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
        boolean verified = member.verifyPassword("secretsecret", passwordEncoder);
        assertThat(verified).isTrue();
        boolean verified2 = member.verifyPassword("hello", passwordEncoder);
        assertThat(verified2).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("JJ Lim");

        member.changeNickname("yamsroun");
        assertThat(member.getNickname()).isEqualTo("yamsroun");
    }

    @Test
    void changePassword() {
        member.changePassword("topsecret", passwordEncoder);
        assertThat(member.verifyPassword("topsecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("invalidEmail"), passwordEncoder))
            .isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var request = new MemberInfoUpdateRequest("yamsroun", "yamsroun", "자기소개");
        member.updateInfo(request);

        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }
}
