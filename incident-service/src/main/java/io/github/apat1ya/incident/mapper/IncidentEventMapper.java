package io.github.apat1ya.incident.mapper;

import event.incident.StateChangedEvent;
import io.github.apat1ya.incident.entity.IncidentEntity;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface IncidentEventMapper {
    StateChangedEvent toEventFromEntity(IncidentEntity incident);
}
