package br.com.cesarmontaldi.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer episodio,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento,
                            @JsonAlias("Actors") String atores,
                            @JsonAlias("Plot") String sinopse,
                            @JsonAlias("Poster") String poster) {
}
