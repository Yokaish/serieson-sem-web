package br.com.serieson.serieson.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe); //usando Generics para poder retornar qualquer tipo
}
