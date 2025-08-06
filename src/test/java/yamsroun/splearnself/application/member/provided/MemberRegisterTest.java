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
        //checkValidation(new MemberRegisterRequest("jj@lim.com", null, "longsecret"));
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
    }
}
