package yamsroun.splearnself.domain;

public record MemberRegisterRequest(
    String email,
    String nickname,
    String password
) {
}
