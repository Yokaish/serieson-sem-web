package br.com.serieson.serieson.principal;

import br.com.serieson.serieson.model.DadosEpisodio;
import br.com.serieson.serieson.model.DadosSerie;
import br.com.serieson.serieson.model.DadosTemporada;
import br.com.serieson.serieson.model.Episodio;
import br.com.serieson.serieson.service.ConsumoAPI;
import br.com.serieson.serieson.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);

    private ConsumoAPI consumoAPI = new ConsumoAPI();

    private ConverteDados conversor = new ConverteDados();


    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=b7c42289";

    //"https://www.omdbapi.com/?t=gilmore+girls&apikey=b7c42289"

    public void exibeMenu() {

        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = scanner.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        var dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season="+ i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}


        temporadas.forEach(t-> t.episodios().forEach(e -> System.out.println(e.titulo())));


        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());

        System.out.println("TOP 5 EPISODIOS");
        dadosEpisodios.stream().filter(e -> !e.avaliacao().equalsIgnoreCase("N/A")).sorted(Comparator.comparing((DadosEpisodio::avaliacao)).reversed()).limit(5).forEach(System.out::println);


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numeroTemporada(), d))
                ).collect(Collectors.toList());


        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episodios? ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                "\nEpisódio: " + e.getTitulo() +
                                "\nData de Lançamento: " + e.getDataLancamento().format(formatador)
                ));

        System.out.println("Digite trecho do título para busca: ");
        var trechoTitulo = scanner.nextLine();

        Optional<Episodio> episodioBuscado = episodios.stream().filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase())).findFirst();
        if (episodioBuscado.isPresent()) {
            Episodio episodioEncontrado = episodioBuscado.get();
            System.out.println("Temporada: " + episodioEncontrado.getTemporada());
            System.out.println(episodioEncontrado);
        } else {
            System.out.println("Nenhum episódio que contém " + trechoTitulo + " encontrado");
        }


        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter( e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));


        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter( e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage() + "\nMelhor episódio: " + est.getMax() + "\nPior episódio: " + est.getMin() + "\nEpisódios avaliados: " + est.getCount());

    }
}

//		temporadas.forEach(System.out::println);

//        for(int i = 0; i < dadosSerie.totalTemporadas(); i++) {
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios(); //pega a temporada do indice
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

//mesma coisa que em cima

//        List<String> nomes = Arrays.asList("Jaque", "Yasmin", "Paulo", "Rodrigo", "Nico");
//
//        nomes.stream().filter(n-> n.startsWith("N")).map(n -> n.toUpperCase()).limit(3).forEach(System.out::println);
