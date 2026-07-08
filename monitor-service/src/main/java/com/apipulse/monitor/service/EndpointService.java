package com.apipulse.monitor.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.apipulse.monitor.dto.endpoint.EndpointActivityDto;
import com.apipulse.monitor.dto.endpoint.EndpointCreateDto;
import com.apipulse.monitor.dto.endpoint.EndpointEditDto;
import com.apipulse.monitor.dto.endpoint.EndpointResponseDto;
import com.apipulse.monitor.entity.EndpointEntity;
import com.apipulse.monitor.entity.EndpointStatus;
import com.apipulse.monitor.exception.InvalidPathException;
import com.apipulse.monitor.mapper.EndpointMapper;
import com.apipulse.monitor.repository.EndpointRepository;
import com.apipulse.monitor.scheduler.EndpointSchedulerService;
import com.apipulse.monitor.service.support.AccessChecker;
import com.apipulse.monitor.service.support.CurrentUserProvider;

@Service
@RequiredArgsConstructor
public class EndpointService {
    private final EndpointRepository endpointRepository;
    private final EndpointMapper endpointMapper;
    private final AccessChecker accessChecker;
    private final CurrentUserProvider userProvider;
    private final EndpointSchedulerService schedulerService;

    public Page<EndpointResponseDto> getAll(Long monitorId, Pageable pageable) {
        return endpointRepository.findAllByMonitorId(pageable, monitorId)
                .map(endpointMapper::toResponseDto);
    }

    public EndpointResponseDto create(Long monitorId, EndpointCreateDto requestDto) {
        accessChecker.checkAccessEdit(userProvider.getCurrentUserId(), monitorId);
        validPath(requestDto.path());
        EndpointEntity endpoint = endpointMapper.toEntityFromCreateDto(requestDto);
        endpoint.setMonitorId(monitorId);
        if (requestDto.checkIntervalSeconds()==null) {
            endpoint.setCheckIntervalSeconds(60);
        }
        endpoint.setFailureThreshold(3);
        endpoint.setRecoveryThreshold(3);
        endpoint.setStatus(EndpointStatus.UP);
        endpoint.setFailureCounter(0);
        endpointRepository.save(endpoint);
        if (endpoint.isActive()) {
            schedulerService.scheduleEndpoint(endpoint.getId(), endpoint.getCheckIntervalSeconds());
        }
        return endpointMapper.toResponseDto(endpoint);
    }

    public EndpointResponseDto edit(Long monitorId, Long endpointId, EndpointEditDto requestDto) {
        accessChecker.checkAccessEdit(userProvider.getCurrentUserId(), monitorId);
        validPath(requestDto.path());
        EndpointEntity endpoint = endpointRepository.findByIdAndMonitorId(endpointId, monitorId)
                .orElseThrow(() -> new EntityNotFoundException("Endpoint not found by id"));
        endpoint.setPath(requestDto.path());
        if (requestDto.checkIntervalSeconds()==null) {
            endpoint.setCheckIntervalSeconds(60);
        }
        endpoint.setHttpMethod(requestDto.httpMethod());
        endpointRepository.save(endpoint);
        if (endpoint.isActive()) {
            schedulerService.scheduleEndpoint(endpoint.getId(), endpoint.getCheckIntervalSeconds());
        }
        return endpointMapper.toResponseDto(endpoint);
    }

    public void delete(Long monitorId, Long endpointId) {
        accessChecker.checkAccessEdit(userProvider.getCurrentUserId(), monitorId);
        EndpointEntity endpoint = endpointRepository.findByIdAndMonitorId(endpointId, monitorId)
                .orElseThrow(() -> new EntityNotFoundException("Endpoint not found by id"));
        schedulerService.cancelEndpoint(endpointId);
        endpointRepository.deleteById(endpointId);
    }

    public EndpointResponseDto active(Long monitorId,
                                      Long endpointId,
                                      EndpointActivityDto activityDto) {
        EndpointEntity endpoint = endpointRepository.findByIdAndMonitorId(endpointId, monitorId)
                .orElseThrow(() -> new EntityNotFoundException("Endpoint not found by id"));
        endpoint.setActive(activityDto.active());
        endpointRepository.save(endpoint);
        if (endpoint.isActive()) {
            schedulerService.scheduleEndpoint(endpointId, endpoint.getCheckIntervalSeconds());
        } else {
            schedulerService.cancelEndpoint(endpointId);
        }
        return endpointMapper.toResponseDto(endpoint);
    }

    public EndpointEntity findById(Long endpointId) {
        return endpointRepository.findById(endpointId)
                .orElseThrow(() -> new EntityNotFoundException("Endpoint not found by id"));
    }

    private void validPath(String path) {
        if (!path.startsWith("/")) {
            throw new InvalidPathException("Endpoint path must start with /");
        }
    }
}
