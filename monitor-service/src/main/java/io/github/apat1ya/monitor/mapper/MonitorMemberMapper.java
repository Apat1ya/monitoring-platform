package io.github.apat1ya.monitor.mapper;

import io.github.apat1ya.monitor.dto.member.MemberResponseDto;
import io.github.apat1ya.monitor.entity.member.MonitorMember;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MonitorMemberMapper {
    MemberResponseDto toResponse(MonitorMember member);
}
