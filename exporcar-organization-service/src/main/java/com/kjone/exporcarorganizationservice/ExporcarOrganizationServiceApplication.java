package com.kjone.exporcarorganizationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ExporcarOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExporcarOrganizationServiceApplication.class, args);
    }

}
