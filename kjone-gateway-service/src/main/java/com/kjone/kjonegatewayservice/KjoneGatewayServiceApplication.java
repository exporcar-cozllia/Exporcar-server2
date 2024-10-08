package com.kjone.kjonegatewayservice;

import com.google.common.net.HttpHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
public class KjoneGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KjoneGatewayServiceApplication.class, args);
    }

    @Bean
    public KeyResolver tokenKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
    }

}
