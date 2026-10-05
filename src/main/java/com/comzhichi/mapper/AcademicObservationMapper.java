package com.comzhichi.mapper;

import com.comzhichi.dto.request.AcademicObservationRequestDTO;
import com.comzhichi.dto.response.AcademicObservationResponseDTO;
import com.comzhichi.model.AcademicObservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AcademicObservationMapper {

    @Mapping(target = "enrollmentId", source = "enrollment.id")
    AcademicObservationResponseDTO toResponse(AcademicObservation observation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enrollment", ignore = true)
    AcademicObservation toEntity(AcademicObservationRequestDTO dto);
}
