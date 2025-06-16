package br.com.serieson.serieson.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonProperty("imdbVotes") String votos)
//JsonProperty serve tanto no processo de serializaçao tanto no deserealizaçao
// (na hora de gerar um json com essa serie ele vai colocar imdbVotes), na hora de escrever usa imdbVotes

@JsonIgnoreProperties(ignoreUnknown = true)  //vai ignorar oq nao for as propriedades abaixo
public record DadosSerie(
        @JsonAlias("Title") String titulo,
        //dando o apelido Title que é como está no json da API, le o title e usa o nome original do atributo (titulo),
        // ele pode procurar um array de palavras de titulo e deserializar para o atributo
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String avaliacao)
    {



    }
