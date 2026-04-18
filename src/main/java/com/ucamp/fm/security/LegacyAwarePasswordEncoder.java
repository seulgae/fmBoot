package com.ucamp.fm.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 신규 비밀번호는 BCrypt로 저장하고, 과거 평문 데이터도 비교 가능하게 처리한다.
 */
public class LegacyAwarePasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null) {
            return false;
        }

        // 인코딩 주의:
        // 과거 데이터가 BCrypt가 아니라 평문으로 저장된 경우를 방어적으로 허용한다.
        // 신규 저장은 항상 BCrypt를 사용한다.
        if (encodedPassword.startsWith("$2a$")
                || encodedPassword.startsWith("$2b$")
                || encodedPassword.startsWith("$2y$")) {
            return delegate.matches(rawPassword, encodedPassword);
        }

        return encodedPassword.contentEquals(rawPassword);
    }
}
