package io.github.apat1ya.incident.mapper;

import io.github.apat1ya.common.event.incident.StateChangedEvent;
import io.github.apat1ya.incident.entity.IncidentEntity;
import io.github.apat1ya.common.mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface IncidentEventMapper {
    StateChangedEvent toEventFromEntity(IncidentEntity incident);
}
