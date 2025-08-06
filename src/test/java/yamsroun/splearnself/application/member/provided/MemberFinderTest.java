package yamsroun.splearnself.application.member.provided;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import yamsroun.splearnself.SplearnTestConfig;
import yamsroun.splearnself.domain.member.Member;
import yamsroun.splearnself.domain.member.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfig.class)
record MemberFinderTest(
    MemberFinder memberFinder,
    MemberRegister memberRegister,
    EntityManager entityManager
) {

    @Test
    void find() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member result = memberFinder.find(member.getId());
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Test
    void findFail() {
        long invalidId = -999;
        assertThatThrownBy(() -> memberFinder.find(invalidId))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
