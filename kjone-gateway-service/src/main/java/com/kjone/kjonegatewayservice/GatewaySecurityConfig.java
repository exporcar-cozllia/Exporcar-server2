package com.kjone.kjonegatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class GatewaySecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/sign/signup", "/v1/sign/signin").permitAll()  // 인증 필요 없음
                        .requestMatchers("/api/**").permitAll()  // 모든 API 접근 허용
                        .requestMatchers(HttpMethod.GET, "/v1/user/me").permitAll()  // 특정 경로에 대해 인증 요구 안함
                        .requestMatchers(HttpMethod.GET, "/v1/sign/signout").authenticated()  // 이 경로는 인증된 사용자만
                        .anyRequest().denyAll()
                );
        return http.build();
    }
}
