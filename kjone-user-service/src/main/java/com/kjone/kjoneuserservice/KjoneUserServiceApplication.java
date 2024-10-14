package com.kjone.kjoneuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class KjoneUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KjoneUserServiceApplication.class, args);
	}

}
