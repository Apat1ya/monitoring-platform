package com.apipulse.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.apipulse.monitor.client.EndpointHttpClient;
import com.apipulse.monitor.dto.endpoint.EndpointHttpResponse;
import com.apipulse.monitor.entity.CheckResult;
import com.apipulse.monitor.entity.EndpointEntity;
import com.apipulse.monitor.repository.CheckResultRepository;

@Service
@RequiredArgsConstructor
public class EndpointCheckService {
    private final EndpointService endpointService;
    private final EndpointHttpClient endpointHttpClient;
    private final MonitorService monitorService;
    private final CheckResultRepository checkResultRepository;
    private final CheckResultHandler incidentService;

    public void checkEndpoint(Long endpointId) {
        EndpointEntity endpoint = endpointService.findById(endpointId);
        String url = monitorService.getBaseUrl(endpoint.getMonitorId()) + endpoint.getPath();
        EndpointHttpResponse response = endpointHttpClient.sendRequest(url, endpoint);

        CheckResult result = new CheckResult();
        result.setMonitorId(endpoint.getMonitorId());
        result.setEndpointId(endpointId);
        result.setCheckedUrl(url);
        result.setSuccess(isSuccess(endpoint, response));
        result.setHttpMethod(endpoint.getHttpMethod());
        result.setStatusCode(response.statusCode());
        result.setCheckedAt(response.responseTime());
        result.setResponseTime(response.responseTime());
        result.setErrorMessage(response.body());
        result.setCheckedAt(response.checkedAt());
        checkResultRepository.save(result);
        incidentService.processCheckResult(endpoint, result);
    }

    private boolean isSuccess(EndpointEntity endpoint, EndpointHttpResponse response) {
        return endpoint.getExpectedStatusCode()==response.statusCode();
    }
}
