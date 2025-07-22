package com.Emanuel.PesquisaDeLivros.PesquisaDeLivros;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ObterDados {
    Scanner busca = new Scanner(System.in);
    var pesquisa = "";
    pesquisa = busca;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://www.googleapis.com/books/v1/volumes?q=" + pesquisa.replace(" ", "+") +
                    "&=AIzaSyBtPosTAVf3OHzasb3iilH0lXx6sMaXwtA"))
            .build();

    HttpResponse<String> response = client
            .send(request, HttpResponse.BodyHandlers.ofString());

    String json = response.body();

    public ObterDados() throws IOException, InterruptedException {
    }
}
