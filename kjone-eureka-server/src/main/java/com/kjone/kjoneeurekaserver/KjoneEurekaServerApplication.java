package com.kjone.kjoneeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class KjoneEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KjoneEurekaServerApplication.class, args);
	}

}
