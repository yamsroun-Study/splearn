package yamsroun.splearnself.application.required;

import org.springframework.data.repository.Repository;

import yamsroun.splearnself.domain.Member;

/**
 * 회원 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);
}
