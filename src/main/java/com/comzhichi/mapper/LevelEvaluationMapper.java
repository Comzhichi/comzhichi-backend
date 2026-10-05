package com.comzhichi.mapper;

import com.comzhichi.dto.request.LevelEvaluationRequestDTO;
import com.comzhichi.dto.response.LevelEvaluationResponseDTO;
import com.comzhichi.model.LevelEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LevelEvaluationMapper {
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    LevelEvaluationResponseDTO toResponse(LevelEvaluation evaluation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    LevelEvaluation toEntity(LevelEvaluationRequestDTO dto);
}
