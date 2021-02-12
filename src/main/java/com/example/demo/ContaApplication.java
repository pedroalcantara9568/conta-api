package com.example.demo;

import com.example.demo.entity.Usuario;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ContaApplication {

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(ContaApplication.class, args);
		JwtService service = context.getBean(JwtService.class);
		final Usuario usuario = Usuario.builder()
				.id("1")
				.login("Cabeça")
				.build();

		;
		System.out.println(service.gerarToken(usuario));
	}

}
