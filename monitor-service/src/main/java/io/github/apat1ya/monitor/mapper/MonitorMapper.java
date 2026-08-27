package io.github.apat1ya.monitor.mapper;

import io.github.apat1ya.monitor.dto.monitor.MonitorRequestDto;
import io.github.apat1ya.monitor.dto.monitor.MonitorResponseDto;
import io.github.apat1ya.monitor.entity.MonitorEntity;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MonitorMapper {

    MonitorResponseDto toResponseDto(MonitorEntity monitor);

    MonitorEntity toEntity(MonitorRequestDto requestDto);
}
