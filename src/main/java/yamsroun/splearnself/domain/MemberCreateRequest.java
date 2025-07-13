package yamsroun.splearnself.domain;

public record MemberCreateRequest(
    String email,
    String nickname,
    String password
) {
}
