package yamsroun.splearnself.application.provided;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import yamsroun.splearnself.domain.Member;
import yamsroun.splearnself.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
@Validated
public interface MemberRegister {

    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);
}
