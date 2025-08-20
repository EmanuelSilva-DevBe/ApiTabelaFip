package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static Scanner s = new Scanner(System.in);
    private static final String ENDERECO_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private static ConverteDados conversor = new ConverteDados();
    static ConsumoApi consumo = new ConsumoApi();

    public static void ExibeMenu() throws JsonProcessingException {
        var menu = """
                *** OPÇÕES ***
                
                1 - Carro
                2 - Moto
                3 - Caminhão
                
                Digite uma das opções: 
                """;
        System.out.println(menu);
        var opcao = s.nextLine();
        System.out.println("\n");

        String endereco;

        if (opcao.toLowerCase().contains("carr") || opcao.toLowerCase().contains("1") ) {
            endereco = ENDERECO_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot") || opcao.toLowerCase().contains("2")) {
            endereco = ENDERECO_BASE + "motos/marcas";

        }else {
            endereco = ENDERECO_BASE + "caminhão/modelos";
        }

        var retornoDados = consumo.obterDados(endereco);
        System.out.println(retornoDados);

        var marcas = conversor.obterLista(retornoDados, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca desejada: ");
        var marca = s.nextLine();

        endereco = endereco+ "/" + marca + "/modelos";

        var json = consumo.obterDados(endereco);
        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\n Digite um trecho do nome do carro a ser buscado: ");
        var nomeVeiculo = s.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo: ");
        var codModelo = s.nextLine();

        endereco = endereco + "/" + codModelo + "/anos";
        json = consumo.obterDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\n Todos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);




    }
}
