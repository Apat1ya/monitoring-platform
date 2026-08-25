package io.github.apat1ya.monitor.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import io.github.apat1ya.monitor.dto.monitor.MonitorRequestDto;
import io.github.apat1ya.monitor.dto.monitor.MonitorResponseDto;
import io.github.apat1ya.monitor.entity.MonitorEntity;
import io.github.apat1ya.monitor.exception.InvalidPathException;
import io.github.apat1ya.monitor.exception.MonitorNotFoundException;
import io.github.apat1ya.monitor.mapper.MonitorMapper;
import io.github.apat1ya.monitor.repository.MemberMonitorRepository;
import io.github.apat1ya.monitor.repository.MonitorRepository;
import io.github.apat1ya.monitor.service.support.AccessChecker;
import io.github.apat1ya.monitor.service.support.CurrentUserProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MonitorService {
    private final MonitorRepository monitorRepository;
    private final MonitorMapper monitorMapper;
    private final CurrentUserProvider userProvider;
    private final AccessChecker accessChecker;
    private final MemberMonitorRepository memberRepository;

    public MonitorResponseDto create(MonitorRequestDto requestDto) {
        validPath(requestDto.target());
        MonitorEntity entity = monitorMapper.toEntity(requestDto);
        entity.setUserId(userProvider.getCurrentUserId());
        monitorRepository.save(entity);
        return monitorMapper.toResponseDto(entity);
    }

    public MonitorResponseDto update(Long monitorId, MonitorRequestDto requestDto) {
        accessChecker.checkAccessEdit(userProvider.getCurrentUserId(), monitorId);
        validPath(requestDto.target());
        MonitorEntity entity = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new MonitorNotFoundException("Monitor not found by id"));
        if (requestDto.target()!=null) {
            entity.setTarget(requestDto.target());
        }
        if (requestDto.description()!=null) {
            entity.setDescription(requestDto.description());
        }
        entity.setActive(requestDto.active());
        monitorRepository.save(entity);
        return monitorMapper.toResponseDto(entity);
    }

    public Page<MonitorResponseDto> getAllMonitors(Pageable pageable) {
        return monitorRepository.findAllByUserId(pageable, userProvider.getCurrentUserId())
                .map(monitorMapper::toResponseDto);
    }

    public void delete(Long monitorId) {
        accessChecker.checkAccessOwner(userProvider.getCurrentUserId(), monitorId);
        MonitorEntity entity = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new MonitorNotFoundException("Monitor not found by id"));
        memberRepository.deleteAllByMonitorId(monitorId);
        monitorRepository.delete(entity);
    }

    public String getBaseUrl(Long monitorId) {
        MonitorEntity monitor = monitorRepository.findById(monitorId)
                .orElseThrow(() -> new EntityNotFoundException("Monitor not found by id"));
        return monitor.getTarget();
    }

    private void validPath(String path) {
        if (!path.endsWith("/")) {
            throw new InvalidPathException("Monitor path must not end with /");
        }
    }
}
