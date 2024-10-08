package com.kjone.kjonegatewayservice;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private final JwtProvider jwtProvider; // JWT를 검증하는 클래스
    private final CookieProvider cookieProvider; // 쿠키 관리 클래스

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtProvider jwtProvider,
                                   CookieProvider cookieProvider) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.cookieProvider = cookieProvider;
    }

    @Override
    protected Mono<Authentication> authenticate(ServerWebExchange exchange) {
        // JWT 토큰을 추출하고 검증하는 로직을 추가
        String token = extractToken(exchange);
        if (token != null && jwtProvider.validateToken(token)) {
            // JWT 검증 로직 수행
            // 필요한 경우 사용자 인증 정보 반환
        }
        return super.authenticate(exchange);
    }

    private String extractToken(ServerWebExchange exchange) {
        // 헤더에서 토큰을 추출하는 로직 추가
        return exchange.getRequest().getHeaders().getFirst("Authorization");
    }
}
