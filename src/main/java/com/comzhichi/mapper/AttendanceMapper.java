package com.comzhichi.mapper;

import com.comzhichi.dto.request.AttendanceRequestDTO;
import com.comzhichi.dto.response.AttendanceResponseDTO;
import com.comzhichi.model.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "enrollmentId", source = "enrollment.id")
    AttendanceResponseDTO toResponse(Attendance attendance);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    Attendance toEntity(AttendanceRequestDTO dto);
}