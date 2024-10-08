package com.kjone.kjonegatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Arrays;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(HttpMethod.POST, "/v1/sign/signup", "/v1/sign/signin").permitAll()
//                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/v1/user/me").hasAnyRole("USER", "ADMIN", "OWNER", "EMPLOYEE")
//                        .requestMatchers(HttpMethod.GET, "/v1/sign/signout").authenticated() // 인증된 유저만
//                        .requestMatchers(HttpMethod.DELETE, "/v1/sign/delete").authenticated() // 인증된 유저만
//                        .requestMatchers("/favicon.ico").permitAll() // Favicon에 대한 접근 허용
//                        .anyRequest().denyAll());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/test/test").permitAll() // 요청 허용
                                .anyRequest().authenticated() // 그 외의 요청은 인증 필요
                )
                .csrf().disable(); // CSRF 비활성화

        return http.build();
    }
}
