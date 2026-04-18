package com.ucamp.fm;

import com.ucamp.fm.security.LegacyAwarePasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 현재 프로젝트의 최소 보안 정책과 암호화 방식을 정의한다.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 인코딩 주의:
        // 기존 DB에 평문 비밀번호가 남아 있을 수 있어 BCrypt와 레거시 평문 비교를 함께 지원한다.
        return new LegacyAwarePasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeRequests(auth -> auth
                        .antMatchers(
                                "/",
                                "/error",
                                "/h2-console/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/icons/**",
                                "/uploadImg/**",
                                "/login/**",
                                "/manifest.webmanifest",
                                "/service-worker.js",
                                "/offline.html"
                        ).permitAll()
                        // 현재 프로젝트는 Spring Security 인증 체인보다
                        // 세션의 m_id 값을 컨트롤러에서 직접 확인하는 구조다.
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
