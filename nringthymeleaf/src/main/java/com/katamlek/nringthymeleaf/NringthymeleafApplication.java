package com.katamlek.nringthymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.katamlek.nringthymeleaf.domain"})
@ComponentScan(basePackages = {"com.katamlek.nringthymeleaf.domain"})
public class NringthymeleafApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NringthymeleafApplication.class, args);
	}
}