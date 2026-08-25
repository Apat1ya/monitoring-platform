package io.github.apat1ya.monitor.service;

import event.CheckResultEvent;
import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.client.EndpointHttpClient;
import io.github.apat1ya.monitor.dto.endpoint.EndpointHttpResponse;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointCheckService {
    private final EndpointService endpointService;
    private final EndpointHttpClient endpointHttpClient;
    private final MonitorService monitorService;

    public void checkEndpoint(Long endpointId) {
        EndpointEntity endpoint = endpointService.findById(endpointId);
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

    }

    private boolean isSuccess(EndpointEntity endpoint, EndpointHttpResponse response) {
        return endpoint.getExpectedStatusCode()==response.statusCode();
    }
}
