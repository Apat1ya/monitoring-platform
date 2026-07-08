package com.apipulse.monitor.mapper;

import com.apipulse.monitor.dto.checkresult.CheckResultResponse;
import com.apipulse.monitor.entity.CheckResult;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface CheckResultMapper {
    CheckResultResponse toResponse(CheckResult result);
}
