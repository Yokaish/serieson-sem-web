package br.com.serieson.serieson;

import br.com.serieson.serieson.model.DadosSerie;
import br.com.serieson.serieson.service.ConsumoAPI;
import br.com.serieson.serieson.service.ConverteDados;
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
		//consome qualquer api, vai sempre buscar a informaçao
		var consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=b7c42289");
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);


	}
}
