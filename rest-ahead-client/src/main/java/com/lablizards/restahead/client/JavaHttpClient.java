package com.lablizards.restahead.client;

import com.lablizards.restahead.client.requests.DeleteRequest;
import com.lablizards.restahead.client.requests.GetRequest;
import com.lablizards.restahead.client.requests.PatchRequest;
import com.lablizards.restahead.client.requests.PostRequest;
import com.lablizards.restahead.client.requests.PutRequest;
import com.lablizards.restahead.client.requests.Request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RestClient} using {@link HttpClient} present in JDK.
 */
public class JavaHttpClient implements RestClient {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String baseUrl;

    /**
     * Creates a new instance of this client.
     *
     * @param baseUrl the url to use as the base of the request
     */
    public JavaHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Future<Response> execute(Request request) {
        var queryString = request.getQueries()
            .entrySet()
            .stream()
            .flatMap(entry -> entry.getValue().stream().map(value -> "%s=%s".formatted(entry.getKey(), value)))
            .collect(Collectors.joining("&"));

        var requestBuilder = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + request.getPath() + "?" + queryString));
        if (request instanceof DeleteRequest) {
            requestBuilder.DELETE();
        } else if (request instanceof GetRequest) {
            requestBuilder.GET();
        } else if (request instanceof PatchRequest) {
            requestBuilder.method("PATCH", HttpRequest.BodyPublishers.noBody());
        } else if (request instanceof PostRequest) {
            requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
        } else if (request instanceof PutRequest) {
            requestBuilder.PUT(HttpRequest.BodyPublishers.noBody());
        }
        request.getHeaders().forEach((name, values) -> values.forEach(value -> requestBuilder.header(name, value)));
        var httpRequest = requestBuilder.build();
        return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofInputStream())
            .thenApply(response -> new Response(response.statusCode(), response.headers().map(), response.body()));
    }
}
