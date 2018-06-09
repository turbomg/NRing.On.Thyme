package com.katamlek.nringthymeleaf;

import com.katamlek.nringthymeleaf.vaadinutils.AppInit;
import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackageClasses = {AppInit.class})
@EntityScan(basePackages = {"com.katamlek.nringthymeleaf.domain"})
@ComponentScan(basePackages = {"com.katamlek.nringthymeleaf.domain"})
@EnableVaadin
public class NringthymeleafApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NringthymeleafApplication.class, args); }

//        @Override
//        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//            return application.sources(AppInit.class);
//	}
}