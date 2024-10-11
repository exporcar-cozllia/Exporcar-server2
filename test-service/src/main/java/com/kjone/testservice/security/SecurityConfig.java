package com.kjone.testservice.security;


import com.kjone.testservice.security.cookie.CookieProvider;
import com.kjone.testservice.security.jwt.JwtAuthenticationFilter;
import com.kjone.testservice.security.jwt.JwtProvider;
import com.kjone.testservice.security.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;
    private final CookieProvider cookieProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/organization/sign/signup", "/v1/organization/sign/signin").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/organization/user/me").authenticated() // 인증된 유저만
                        .requestMatchers(HttpMethod.POST, "/v1/organization/create").hasRole("OWNER") //권한이 OWNER인 유저만
                        .requestMatchers(HttpMethod.GET, "/v1/organization/get_organization").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE") //조직 조회 권한
                        .requestMatchers(HttpMethod.GET, "/v1/organization/sign/signout").authenticated() // 인증된 유저만
                        .requestMatchers(HttpMethod.DELETE, "/v1/organization/sign/delete").authenticated() // 인증된 유저만
                        .requestMatchers("/favicon.ico").permitAll() // Favicon에 대한 접근 허용
                        .anyRequest().denyAll());

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider,cookieProvider, customUserDetailService),  UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
