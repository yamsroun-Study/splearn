package yamsroun.splearnself.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import yamsroun.splearnself.SplearnTestConfig;
import yamsroun.splearnself.domain.member.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("ImplicitSubclassInspection")
@SpringBootTest
@Transactional
@Import(SplearnTestConfig.class)
    //@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
record MemberRegisterTest(
    MemberRegister memberRegister,
    EntityManager entityManager
) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        System.out.println(">>> member: " + member);

        assertThat(member.getId()).isPositive();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
            .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("jj@lim.com", "JJ", "longsecret"));
        checkValidation(new MemberRegisterRequest("jj@lim.com", "JJ Lim JJ Lim JJ Lim JJ Lim", "longsecret"));
        checkValidation(new MemberRegisterRequest("jj-lim.com", "JJ Lim", "longsecret"));
        //checkValidation(new MemberRegisterRequest("jj@lim.com", null, "longsecret"))
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void deactivate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        var request = new MemberInfoUpdateRequest("jjlim", "yamsroun", "자기소개");
        member = memberRegister.updateInfo(member.getId(), request);

        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        Long memberId = member.getId();
        memberRegister.activate(memberId);

        var request = new MemberInfoUpdateRequest("jjlim", "yamsroun", "자기소개");
        memberRegister.updateInfo(memberId, request);

        Member otherMember = memberRegister.register(MemberFixture.createMemberRegisterRequest("other@abc.com"));
        Long otherMemberId = otherMember.getId();
        memberRegister.activate(otherMemberId);
        entityManager.flush();
        entityManager.clear();

        // otherMember는 기존 회원과 같은 프로필 주소를 사용할 수 없다
        var request2 = new MemberInfoUpdateRequest("jjlim2", "yamsroun", "자기소개2");
        assertThatThrownBy(() -> memberRegister.updateInfo(otherMemberId, request2))
            .isInstanceOf(DuplicateProfileException.class);

        // 다른 프로필 주소로는 변경 가능
        var request3 = new MemberInfoUpdateRequest("jjlim2", "yamsroun2", "자기소개2");
        memberRegister.updateInfo(otherMemberId, request3);

        // 기존 프로필 주소를 바꾸는 것도 가능
        var request4 = new MemberInfoUpdateRequest("jjlim", "yamsroun", "자기소개");
        memberRegister.updateInfo(memberId, request4);

        // 프로필 주소를 제거하는 것도 가능
        var request5 = new MemberInfoUpdateRequest("jjlim", "", "자기소개");
        memberRegister.updateInfo(memberId, request5);

        // 프로필 주소 중복은 허용하지 않음
        var request6 = new MemberInfoUpdateRequest("jjlim", "yamsroun2", "자기소개");
        assertThatThrownBy(() -> memberRegister.updateInfo(memberId, request6))
            .isInstanceOf(DuplicateProfileException.class);
    }
}
