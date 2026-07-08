package com.apipulse.monitor.mapper;

import com.apipulse.monitor.dto.monitor.MonitorRequestDto;
import com.apipulse.monitor.dto.monitor.MonitorResponseDto;
import com.apipulse.monitor.entity.MonitorEntity;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MonitorMapper {

    MonitorResponseDto toResponseDto(MonitorEntity monitor);

    MonitorEntity toEntity(MonitorRequestDto requestDto);
}
