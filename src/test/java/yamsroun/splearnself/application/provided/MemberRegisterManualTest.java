//package yamsroun.splearnself.application.provided;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.test.util.ReflectionTestUtils;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import lombok.Getter;
//
//import yamsroun.splearnself.application.MemberService;
//import yamsroun.splearnself.application.required.EmailSender;
//import yamsroun.splearnself.application.required.MemberRepository;
//import yamsroun.splearnself.domain.Email;
//import yamsroun.splearnself.domain.Member;
//import yamsroun.splearnself.domain.MemberFixture;
//import yamsroun.splearnself.domain.MemberStatus;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//
//class MemberRegisterManualTest {
//
//    @Test
//    void registerTestStub() {
//        MemberRegister memberRegister = new MemberService(
//            new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
//        );
//        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isPositive();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//    }
//
//    @Test
//    void registerTestMock() {
//        EmailSenderMock emailSenderMock = new EmailSenderMock();
//        MemberRegister memberRegister = new MemberService(
//            new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
//        );
//        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isPositive();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
//    }
//
//    @Test
//    void registerTestMockito() {
//        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);
//        MemberRegister memberRegister = new MemberService(
//            new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
//        );
//        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isPositive();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
//    }
//
//
//    static class MemberRepositoryStub implements MemberRepository {
//
//        @Override
//        public Member save(Member member) {
//            ReflectionTestUtils.setField(member, "id", 1L);
//            return member;
//        }
//
//        @Override
//        public Optional<Member> findByEmail(Email email) {
//            return Optional.empty();
//        }
//
//        @Override
//        public Optional<Member> findById(Long memberId) {
//            return Optional.empty();
//        }
//    }
//
//    static class EmailSenderStub implements EmailSender {
//
//        @Override
//        public void send(Email email, String subject, String body) {
//
//        }
//    }
//
//    @Getter
//    static class EmailSenderMock implements EmailSender {
//
//        List<Email> tos = new ArrayList<>();
//
//        @Override
//        public void send(Email email, String subject, String body) {
//            tos.add(email);
//        }
//    }
//}
