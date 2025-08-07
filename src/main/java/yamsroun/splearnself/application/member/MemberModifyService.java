package yamsroun.splearnself.application.member;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import yamsroun.splearnself.application.member.provided.MemberFinder;
import yamsroun.splearnself.application.member.provided.MemberRegister;
import yamsroun.splearnself.application.member.required.EmailSender;
import yamsroun.splearnself.application.member.required.MemberRepository;
import yamsroun.splearnself.domain.member.*;
import yamsroun.splearnself.domain.shared.Email;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);

        sendWelcomeEmail(member);
        return member;
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다: " + registerRequest.email());
        }
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.activate();

        // Spring Data는 JPA 뿐 아니라 10개 이상의 Repository 기술을 추상화해놓은 것이다.
        // Update 시에도 save()를 호출해야 Repository 기술 변경 시, 수정이 발생하지 않는다.
        // 또한, Spring Data는 Domain Event Publication을 지원하는데, Update 시에도 save()를 호출해야만 이벤트가 발행된다.
        // Auditing을 위해서도 save() 호출이 필요하다. -> 정확한 이유는 모르겠음
        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.deactivate();
        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest updateRequest) {
        Member member = memberFinder.find(memberId);
        member.updateInfo(updateRequest);
        return memberRepository.save(member);
    }
}
