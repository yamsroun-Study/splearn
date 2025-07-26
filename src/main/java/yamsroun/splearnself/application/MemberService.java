package yamsroun.splearnself.application;


import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import yamsroun.splearnself.application.provided.MemberRegister;
import yamsroun.splearnself.application.required.EmailSender;
import yamsroun.splearnself.application.required.MemberRepository;
import yamsroun.splearnself.domain.Member;
import yamsroun.splearnself.domain.MemberRegisterRequest;
import yamsroun.splearnself.domain.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check
        // TODO

        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");

        return member;
    }
}
