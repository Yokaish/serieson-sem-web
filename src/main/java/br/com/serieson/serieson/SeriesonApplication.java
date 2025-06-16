package br.com.serieson.serieson;

import br.com.serieson.serieson.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeriesonApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SeriesonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal();
		principal.exibeMenu();

	}
}
