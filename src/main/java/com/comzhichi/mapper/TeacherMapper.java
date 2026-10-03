package com.comzhichi.mapper;

import com.comzhichi.dto.request.TeacherRequestDTO;
import com.comzhichi.dto.response.TeacherResponseDTO;
import com.comzhichi.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface TeacherMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userEmail", source = "email")
    TeacherResponseDTO toResponse(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequestDTO dto);
}
