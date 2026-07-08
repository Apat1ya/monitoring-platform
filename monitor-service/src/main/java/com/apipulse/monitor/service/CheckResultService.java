package com.apipulse.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.apipulse.monitor.dto.checkresult.CheckResultResponse;
import com.apipulse.monitor.mapper.CheckResultMapper;
import com.apipulse.monitor.repository.CheckResultRepository;

@Service
@RequiredArgsConstructor
public class CheckResultService {
    private final CheckResultRepository repository;
    private final CheckResultMapper checkResultMapper;

    public Page<CheckResultResponse> getAll(Long endpointId, Pageable pageable) {
        return repository.findAllByEndpointId(pageable, endpointId)
                .map(checkResultMapper::toResponse);
    }
}
