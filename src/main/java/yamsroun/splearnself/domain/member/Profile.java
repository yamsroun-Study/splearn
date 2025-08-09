package yamsroun.splearnself.domain.member;

import java.util.Objects;
import java.util.regex.Pattern;

public record Profile(String address) {

    private static final Pattern PROFILE_PATTERN = Pattern.compile("[a-z0-9]+");

    public Profile {
        if (address == null || (!address.isBlank() && address.length() > 15)) {
            throw new IllegalArgumentException("프로필 주소는 최대 15자리를 넘을 수 없습니다.");
        }
        if (!address.isBlank() && !PROFILE_PATTERN.matcher(Objects.requireNonNull(address)).matches()) {
            throw new IllegalArgumentException("프로필 주소 형식이 바르지 않습니다: " + address);
        }
    }

    public String url() {
        return "@" + address;
    }
}
