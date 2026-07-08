package com.apipulse.monitor.mapper;

import com.apipulse.monitor.dto.endpoint.EndpointCreateDto;
import com.apipulse.monitor.dto.endpoint.EndpointResponseDto;
import com.apipulse.monitor.entity.EndpointEntity;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EndpointMapper {
    EndpointResponseDto toResponseDto(EndpointEntity endpoint);

    EndpointEntity toEntityFromCreateDto(EndpointCreateDto requestDto);
}
