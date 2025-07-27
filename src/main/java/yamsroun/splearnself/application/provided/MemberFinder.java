package yamsroun.splearnself.application.provided;

import yamsroun.splearnself.domain.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {

    Member find(Long memberId);
}
