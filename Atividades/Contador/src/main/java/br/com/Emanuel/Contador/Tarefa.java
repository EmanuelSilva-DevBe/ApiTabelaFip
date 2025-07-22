package br.com.Emanuel.Contador;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Tarefa(@JsonProperty("Descrição da atividade") String descricao, @JsonProperty("Status da atividade")
boolean concluida, @JsonProperty("Pessoa responsável") String pessoaResponsavel) {

    @Override
    public String toString() {
        return "\nDescrição da tarefa: " + descricao +
                "\nConcluída: " + concluida +
                "\nPessoa responsável: " + pessoaResponsavel;
    }
}
