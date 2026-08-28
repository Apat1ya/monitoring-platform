package io.github.apat1ya.monitor.service;

import event.CheckResultEvent;
import io.github.apat1ya.monitor.client.EndpointHttpClient;
import io.github.apat1ya.monitor.dto.endpoint.EndpointHttpResponse;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import io.github.apat1ya.monitor.exception.EndpointNotFoundException;
import io.github.apat1ya.monitor.messaging.producer.CheckResultProducer;
import io.github.apat1ya.monitor.repository.EndpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointCheckService {
    private final EndpointRepository endpointRepository;
    private final EndpointHttpClient endpointHttpClient;
    private final MonitorService monitorService;
    private final CheckResultProducer producer;

    public void checkEndpoint(Long endpointId) {
        EndpointEntity endpoint = endpointRepository.findById(endpointId)
                .orElseThrow(() -> new EndpointNotFoundException ("Endpoint Not Found"));
        String url = monitorService.getBaseUrl(endpoint.getMonitorId()) + endpoint.getPath();
        EndpointHttpResponse response = endpointHttpClient.sendRequest(url, endpoint);

        CheckResultEvent checkResultEvent = new CheckResultEvent(
                endpoint.getMonitorId(),
                endpointId,
                endpoint.getStatus().toString(),
                url,
                isSuccess(endpoint, response),
                endpoint.getHttpMethod().name(),
                response.statusCode(),
                response.responseTime(),
                response.body(),
                response.checkedAt()
        );
        producer.send(checkResultEvent);
    }

    private boolean isSuccess(EndpointEntity endpoint, EndpointHttpResponse response) {
        return endpoint.getExpectedStatusCode()==response.statusCode();
    }
}
