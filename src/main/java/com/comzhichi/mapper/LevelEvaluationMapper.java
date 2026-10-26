package com.comzhichi.mapper;

import com.comzhichi.dto.request.LevelEvaluationRequestDTO;
import com.comzhichi.dto.response.LevelEvaluationResponseDTO;
import com.comzhichi.model.LevelEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LevelEvaluationMapper {

    @Mapping(target = "enrollmentId", source = "enrollment.id")
    @Mapping(target = "studentFullName", source = "enrollment.student.user.fullName")
    @Mapping(target = "courseName", source = "enrollment.section.course.name")
    @Mapping(target = "language", source = "enrollment.section.course.language")
    LevelEvaluationResponseDTO toResponse(LevelEvaluation evaluation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    LevelEvaluation toEntity(LevelEvaluationRequestDTO dto);
}