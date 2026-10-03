package com.comzhichi.mapper;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.model.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface ParentMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userEmail", source = "email")
    ParentResponseDTO toResponse(Parent parent);

    @Mapping(target = "id", ignore = true)
    Parent toEntity(ParentRequestDTO dto);
}
