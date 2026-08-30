package io.github.apat1ya.monitor.service;

import event.EndpointStatusChangedEvent;
import io.github.apat1ya.monitor.dto.endpoint.EndpointActivityDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointCreateDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointEditDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointResponseDto;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import io.github.apat1ya.monitor.entity.EndpointStatus;
import io.github.apat1ya.monitor.exception.EndpointNotFoundException;
import io.github.apat1ya.monitor.exception.InvalidPathException;
import io.github.apat1ya.monitor.mapper.EndpointMapper;
import io.github.apat1ya.monitor.repository.EndpointRepository;
import io.github.apat1ya.monitor.scheduler.EndpointSchedulerService;
import io.github.apat1ya.monitor.service.support.AccessChecker;
import io.github.apat1ya.monitor.service.support.CurrentUserProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EndpointService {
    private final EndpointRepository endpointRepository;
    private final EndpointMapper endpointMapper;
    private final AccessChecker accessChecker;
    private final CurrentUserProvider userProvider;
    private final EndpointSchedulerService schedulerService;

    public Page<EndpointResponseDto> getAll(Long monitorId, Pageable pageable) {
        accessChecker.checkAccessView(userProvider.getCurrentUserId(), monitorId);
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
        endpointRepository.delete(endpoint);
    }

    public EndpointResponseDto active(Long monitorId,
                                      Long endpointId,
                                      EndpointActivityDto activityDto) {
        accessChecker.checkAccessEdit(userProvider.getCurrentUserId(), monitorId);
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

    public void handle(EndpointStatusChangedEvent event) {
        EndpointEntity endpoint = endpointRepository.findById(event.endpointId())
                .orElseThrow(() -> new EndpointNotFoundException("Endpoint not found by id"));

        endpoint.setStatus(EndpointStatus.valueOf(event.status()));
        endpointRepository.save(endpoint);
    }
}
