package br.com.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:message.properties")
public class MeApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeApplication.class, args);
	}
}
