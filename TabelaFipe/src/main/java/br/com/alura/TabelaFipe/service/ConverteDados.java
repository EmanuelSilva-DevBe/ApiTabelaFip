package br.com.alura.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collections;
import java.util.List;

public class ConverteDados implements IConverteDados {
    // Classe responsável por converter o JSON recebido da API para o tipo de classe especificado
    private ObjectMapper mapper = new ObjectMapper();

    //Converte o JSON para o tipo de classe especificado
    @Override
    public <T> T obterDados(String json, Class<T> classe)//Converte o JSON para o tipo de classe especificado
    {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> obterLista(String json, Class<T> classe) throws JsonProcessingException {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try {
            return mapper.readValue(json, lista);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
