package io.github.apat1ya.monitor.client;

import io.github.apat1ya.monitor.dto.endpoint.EndpointHttpResponse;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class EndpointHttpClient {
    private final WebClient webClient;

    public EndpointHttpResponse sendRequest(String url, EndpointEntity endpoint) {
        Instant checkedAt = Instant.now();
        long start = System.currentTimeMillis();
        try {
            WebClient.RequestBodySpec requestBodySpec = webClient
                    .method(endpoint.getHttpMethod())
                    .uri(url);
            WebClient.RequestHeadersSpec<?> request;
            if (endpoint.getBody() != null && !endpoint.getBody().isBlank()) {
                request = requestBodySpec
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(endpoint.getBody());
            } else {
                request = requestBodySpec;
            }

            return request
                    .exchangeToMono(response -> response.toEntity(String.class))
                    .map(responseEntity -> new EndpointHttpResponse(
                            responseEntity.getStatusCode().value(),
                            responseEntity.getBody(),
                            System.currentTimeMillis() - start,
                            checkedAt
                    ))
                    .block();
        } catch (Exception e) {
            return new EndpointHttpResponse(
                    null,
                    e.getMessage(),
                    System.currentTimeMillis() - start,
                    checkedAt
            );
        }
    }
}
