package br.com.cesarmontaldi.principal;

import br.com.cesarmontaldi.model.DadosSerie;
import br.com.cesarmontaldi.model.DadosTemporada;
import br.com.cesarmontaldi.model.Serie;
import br.com.cesarmontaldi.service.ConsumoAPI;
import br.com.cesarmontaldi.service.ConverterDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverterDados converter = new ConverterDados();

    private final String URL_API = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_API_KEY");
    private List<DadosSerie> listSeries = new ArrayList<>();

    public void exibeMenu() {
        var opcao = -1;

        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas  
                    0 - Sair                                 
                    """;

            System.out.print(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        listSeries.add(dados);
        System.out.println("\nTitulo: " + dados.title() + " \n" +
                            "Temporadas: " + dados.totalTemporadas() + " \n" +
                            "Genero: " + dados.genero() + " \n" +
                            "Atores: " + dados.atores() + " \n" +
                            "Avaliação: " + dados.avaliacao() + " \n" +
                            "Poster: " + dados.poster() + " \n");
    }

    private DadosSerie getDadosSerie() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = scanner.nextLine();
        var json = consumoAPI.obterDados(URL_API + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = converter.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        DadosSerie dadosSerie = getDadosSerie();
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            var json = consumoAPI.obterDados(URL_API + dadosSerie.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = converter.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);
    }

    private void listarSeriesBuscadas() {
        List<Serie> series = new ArrayList<>();
        series = listSeries.stream()
                        .map(d -> new Serie(d))
                            .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
    }

}
