package com.kjone.kjoneorganizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KjoneOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KjoneOrganizationServiceApplication.class, args);
    }

}
