package br.com.cesarmontaldi;

import br.com.cesarmontaldi.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(ApiScreenMatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
