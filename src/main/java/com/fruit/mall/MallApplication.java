package com.fruit.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
