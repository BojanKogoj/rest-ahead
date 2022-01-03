package com.lablizards.restahead.demo;

import com.lablizards.restahead.adapter.DefaultAdapters;
import com.lablizards.restahead.client.RestClient;
import com.lablizards.restahead.client.requests.DeleteRequest;
import com.lablizards.restahead.conversion.Converter;
import com.lablizards.restahead.exceptions.RequestFailedException;
import com.lablizards.restahead.exceptions.RestException;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Override;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import javax.annotation.processing.Generated;

@Generated("Generated by RestAhead")
public final class ServiceWithUnknownResponse$Impl implements ServiceWithUnknownResponse {
    private final RestClient client;

    private final Converter converter;

    private final DefaultAdapters defaultAdapters;

    public ServiceWithUnknownResponse$Impl(RestClient client, Converter converter,
                                           DefaultAdapters defaultAdapters) {
        this.client = client;
        this.converter = converter;
        this.defaultAdapters = defaultAdapters;
    }

    @Override
    public final ServiceWithUnknownResponse.TestResponse delete() {
        var httpRequest = new DeleteRequest("/delete");
        var response = client.execute(httpRequest);
        CompletableFuture<ServiceWithUnknownResponse.TestResponse> convertedResponse = response.thenApply(r -> {
            if (r.status() < 200 || r.status() >= 300) {
                throw new RequestFailedException(r.status(), r.body());
            }
            try {
                return converter.deserialize(r, ServiceWithUnknownResponse.TestResponse.class);
            } catch (IOException exception) {
                throw new CompletionException(exception);
            }
        } );
        try {
            return defaultAdapters.syncAdapter(convertedResponse);
        } catch (ExecutionException | InterruptedException exception) {
            throw RestException.getAppropriateException(exception);
        }
    }
}