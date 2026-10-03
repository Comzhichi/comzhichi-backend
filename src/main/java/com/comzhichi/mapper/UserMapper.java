package com.comzhichi.mapper;

import com.comzhichi.dto.response.UserResponseDTO;
import com.comzhichi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDTO toResponse(User user);
}