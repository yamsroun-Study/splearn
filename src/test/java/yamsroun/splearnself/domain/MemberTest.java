package yamsroun.splearnself.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("jj@lim.com", "JJ", "secret");
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}
