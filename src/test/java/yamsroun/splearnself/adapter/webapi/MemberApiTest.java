package yamsroun.splearnself.adapter.webapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;

import yamsroun.splearnself.adapter.webapi.dto.MemberRegisterResponse;
import yamsroun.splearnself.application.member.provided.MemberRegister;
import yamsroun.splearnself.application.member.required.MemberRepository;
import yamsroun.splearnself.domain.member.Member;
import yamsroun.splearnself.domain.member.MemberFixture;
import yamsroun.splearnself.domain.member.MemberRegisterRequest;
import yamsroun.splearnself.domain.member.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static yamsroun.splearnself.AssertThatUtils.equalsTo;
import static yamsroun.splearnself.AssertThatUtils.notNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // REQUIRES_NEW, NESTED 방식을 사용하는 경우 Rollback 되지 않음; 별도 스레드에서 실행하는 경우도 마찬가지.
@RequiredArgsConstructor
class MemberApiTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;
    final MemberRepository memberRepository;
    final MemberRegister memberRegister;

    @Test
    void register() throws JsonProcessingException, UnsupportedEncodingException {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post()
            .uri("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange();
        assertThat(result).apply(print())
            .hasStatusOk()
            .bodyJson()
            .hasPathSatisfying("$.memberId", notNull())
            .hasPathSatisfying("$.email", equalsTo(request));

        MemberRegisterResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), MemberRegisterResponse.class);

        Member member = memberRepository.findById(response.memberId()).orElseThrow();
        assertThat(member.getEmail().address()).isEqualTo(request.email());
        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void ducplicateEmail() throws JsonProcessingException {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post()
            .uri("/api/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)
            .exchange();
        assertThat(result).apply(print())
            .hasStatus(HttpStatus.CONFLICT);
    }
}
