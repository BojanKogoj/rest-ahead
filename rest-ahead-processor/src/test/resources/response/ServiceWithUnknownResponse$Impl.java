package com.lablizards.restahead.demo;

import com.lablizards.restahead.client.RestClient;
import com.lablizards.restahead.client.requests.DeleteRequest;
import com.lablizards.restahead.conversion.Converter;
import com.lablizards.restahead.exceptions.RequestFailedException;
import com.lablizards.restahead.exceptions.RestException;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.Override;
import java.util.concurrent.ExecutionException;
import javax.annotation.processing.Generated;

@Generated("Generated by RestAhead")
public final class ServiceWithUnknownResponse$Impl implements ServiceWithUnknownResponse {
    private final RestClient client;

    private final Converter converter;

    public ServiceWithUnknownResponse$Impl(RestClient client, Converter converter) {
        this.client = client;
        this.converter = converter;
    }

    @Override
    public final ServiceWithUnknownResponse.TestResponse delete() {
        var httpRequest = new DeleteRequest("/delete");
        try {
            var response = client.execute(httpRequest).get();
            if (response.status() < 200 || response.status() >= 300) {
                throw new RequestFailedException(response.status(), response.body());
            }
            return converter.deserialize(response, ServiceWithUnknownResponse.TestResponse.class);
        } catch (ExecutionException exception) {
            throw new RestException(exception.getCause());
        } catch (InterruptedException | IOException exception) {
            throw new RestException(exception);
        }
    }
}