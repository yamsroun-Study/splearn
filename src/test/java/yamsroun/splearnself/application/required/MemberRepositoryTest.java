package yamsroun.splearnself.application.required;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import yamsroun.splearnself.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static yamsroun.splearnself.domain.MemberFixture.createMemberRegisterRequest;
import static yamsroun.splearnself.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);
        entityManager.flush();

        assertThat(member.getId()).isPositive();
    }
}
