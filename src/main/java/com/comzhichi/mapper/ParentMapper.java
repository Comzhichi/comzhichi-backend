package com.comzhichi.mapper;

import com.comzhichi.dto.request.ParentRequestDTO;
import com.comzhichi.dto.response.ParentResponseDTO;
import com.comzhichi.model.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    ParentResponseDTO toResponse(Parent parent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Parent toEntity(ParentRequestDTO dto);
}