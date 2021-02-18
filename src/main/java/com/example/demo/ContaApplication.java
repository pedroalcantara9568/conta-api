package com.example.demo;

import com.example.demo.entity.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ContaApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(ContaApplication.class, args);

	}

}
