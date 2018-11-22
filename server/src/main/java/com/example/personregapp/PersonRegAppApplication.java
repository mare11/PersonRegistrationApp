package com.example.personregapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.personregapp")
public class PersonRegAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonRegAppApplication.class, args);
	}
}
