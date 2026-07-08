package com.apipulse.monitor.mapper;

import com.apipulse.monitor.dto.member.MemberResponseDto;
import com.apipulse.monitor.entity.member.MonitorMember;
import mapper.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MonitorMemberMapper {
    MemberResponseDto toResponse(MonitorMember member);
}
