package com.subsTracker.subs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SubsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubsApplication.class, args);
	}

}
