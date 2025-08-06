package yamsroun.splearnself.application.member;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import yamsroun.splearnself.application.member.provided.MemberFinder;
import yamsroun.splearnself.application.member.required.MemberRepository;
import yamsroun.splearnself.domain.member.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    }
}
