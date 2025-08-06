package yamsroun.splearnself.application.member.provided;

import yamsroun.splearnself.domain.member.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {

    Member find(Long memberId);
}
