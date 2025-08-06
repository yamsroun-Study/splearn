package yamsroun.splearnself.domain.member;

public record MemberInfoUpdateRequest(
    String nickname,
    String profileAddress,
    String introduction
) {
}
