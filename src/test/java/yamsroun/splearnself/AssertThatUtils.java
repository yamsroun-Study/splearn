package yamsroun.splearnself;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.Assertions;
import org.springframework.test.json.JsonPathValueAssert;
import java.util.function.Consumer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import yamsroun.splearnself.domain.member.MemberRegisterRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertThatUtils {

    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> Assertions.assertThat(value).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return value -> Assertions.assertThat(value).isEqualTo(request.email());
    }
}
