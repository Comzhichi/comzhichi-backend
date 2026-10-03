package com.comzhichi.mapper;

import com.comzhichi.dto.request.CoordinatorRequestDTO;
import com.comzhichi.dto.response.CoordinatorResponseDTO;
import com.comzhichi.model.Coordinator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CoordinatorMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userEmail", source = "email")
    CoordinatorResponseDTO toResponse(Coordinator coordinator);

    @Mapping(target = "id", ignore = true)
    Coordinator toEntity(CoordinatorRequestDTO dto);
}
