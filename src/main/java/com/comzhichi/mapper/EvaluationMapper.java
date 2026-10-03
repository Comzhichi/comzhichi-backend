package com.comzhichi.mapper;

import com.comzhichi.dto.request.EvaluationRequestDTO;
import com.comzhichi.dto.response.EvaluationResponseDTO;
import com.comzhichi.model.Evaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EvaluationMapper {

    @Mapping(target = "enrollmentId", source = "enrollment.id")
    EvaluationResponseDTO toResponse(Evaluation evaluation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    Evaluation toEntity(EvaluationRequestDTO dto);
}
