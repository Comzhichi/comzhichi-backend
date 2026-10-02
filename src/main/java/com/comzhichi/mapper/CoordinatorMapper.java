package com.comzhichi.mapper;

import com.comzhichi.dto.request.CoordinatorRequestDTO;
import com.comzhichi.dto.response.CoordinatorResponseDTO;
import com.comzhichi.model.Coordinator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CoordinatorMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    CoordinatorResponseDTO toResponse(Coordinator coordinator);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Coordinator toEntity(CoordinatorRequestDTO dto);
}