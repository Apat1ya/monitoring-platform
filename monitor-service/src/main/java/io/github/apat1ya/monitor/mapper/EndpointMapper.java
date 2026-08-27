package io.github.apat1ya.monitor.mapper;

import io.github.apat1ya.monitor.dto.endpoint.EndpointCreateDto;
import io.github.apat1ya.monitor.dto.endpoint.EndpointResponseDto;
import io.github.apat1ya.monitor.entity.EndpointEntity;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EndpointMapper {
    EndpointResponseDto toResponseDto(EndpointEntity endpoint);

    EndpointEntity toEntityFromCreateDto(EndpointCreateDto requestDto);
}
