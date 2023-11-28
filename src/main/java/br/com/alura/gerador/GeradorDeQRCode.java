package br.com.alura.gerador;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

public class GeradorDeQRCode {

    public static void main(String[] args) {
        try {
            //Disparar uma requisição HTTP para a API NINJAS
            var informacoes = "https://alura.com.br";
            var client = HttpClient.newHttpClient();
            var request = HttpRequest
                    .newBuilder()
                    .uri(URI.create("https://api.api-ninjas.com/v1/qrcode?format=png&data=" + informacoes))
                    .header("X-Api-Key", System.getenv("API_NINJA_KEY"))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("STATUS: " +response.statusCode());
            //System.out.println(response.body());

            //CRIAR UM ARQUIVO PNG E SALVAR NO COMPUTADOR
            var arquivoQRCode = Path.of("qrcode.png");
            var bytesDaImagem = Base64.getDecoder().decode(response.body());

            Files.write(arquivoQRCode, bytesDaImagem, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao chamar a API!");
            e.printStackTrace();
        }
    }

}
