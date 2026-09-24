package com.gymManage.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class DBConnection {

    // Configuración REST API del proyecto la url y la key se obtienen de una variable de entorno,
    // si no la tienes pide la
    private static final String URL =
            System.getenv("SUPABASE_URL")+ "/rest/v1/";

    private static final String KEY =
            System.getenv("SUPABASE_KEY");

    static{
        if(URL == null || KEY == null){
            throw new IllegalStateException(
                      "Faltan las variables SUPABASE_URL o SUPABASE_KEY"
            );

        }
    }

    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static final ObjectMapper JSON = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .findAndRegisterModules();

    public static CompletableFuture<String> getAsync(String path) {
        return sendAsync("GET", path, null);
    }

    public static CompletableFuture<String> postAsync(String path, String jsonBody) {
        return sendAsync("POST", path, jsonBody);
    }

    private static CompletableFuture<String> sendAsync(String metodo, String path, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(URL + path))
                .header("apikey", KEY)
                .header("Content-Type", "application/json")
                .header("Prefer", "return=representation");

        builder = switch (metodo) {
            case "POST" -> builder.POST(HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
            case "PATCH" -> builder.method("PATCH", HttpRequest.BodyPublishers.ofString(body != null ? body : ""));
            case "DELETE" -> builder.DELETE();
            default -> builder.GET();
        };

        return HTTP.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() >= 400) {
                        throw new RuntimeException("Error Supabase (" + response.statusCode() + "): " + response.body());
                    }
                    return response.body();
                });
    }
}