package com.anshika_project.user_onboarding.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HtdsDummyAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(HtdsDummyAppApplication.class, args);
	}
}
