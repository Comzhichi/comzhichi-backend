package com.comzhichi.mapper;

import com.comzhichi.dto.response.NotificationResponseDTO;
import com.comzhichi.model.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponseDTO toResponse(Notification notification);
}