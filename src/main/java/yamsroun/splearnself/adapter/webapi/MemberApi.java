package yamsroun.splearnself.adapter.webapi;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import yamsroun.splearnself.adapter.webapi.dto.MemberRegisterResponse;
import yamsroun.splearnself.application.member.provided.MemberRegister;
import yamsroun.splearnself.domain.member.Member;
import yamsroun.splearnself.domain.member.MemberRegisterRequest;

@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@Valid @RequestBody MemberRegisterRequest request) {
        Member member = memberRegister.register(request);
        return MemberRegisterResponse.of(member);
    }

}
