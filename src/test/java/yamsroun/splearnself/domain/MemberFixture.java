package yamsroun.splearnself.domain;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("jj@lim.com");
    }

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "JJ Lim", "secretsecret");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {

            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }
}
