package com.katamlek.nringthymeleaf;

import com.katamlek.nringthymeleaf.vaadinutils.AppUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
        (scanBasePackageClasses = {AppUI.class})
@EntityScan(basePackages = {"com.katamlek.nringthymeleaf.domain", "com.katamlek.nringthymeleaf.repositories", "com.katamlek.nringthymeleaf.vaadinutils"})
@ComponentScan(basePackages = {"com.katamlek.nringthymeleaf", "com.katamlek.nringthymeleaf.domain", "com.katamlek.nringthymeleaf.repositories", "com.katamlek.nringthymeleaf.vaadinutils"})
public class NringthymeleafApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(NringthymeleafApplication.class, args); }

}