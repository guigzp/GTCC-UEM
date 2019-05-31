package br.com.gtcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
@SpringBootApplication
public class GtccApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtccApplication.class, args);
	}
}
