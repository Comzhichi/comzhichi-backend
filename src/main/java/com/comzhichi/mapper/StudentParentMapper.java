package com.comzhichi.mapper;

import com.comzhichi.dto.request.StudentParentRequestDTO;
import com.comzhichi.dto.response.StudentParentResponseDTO;
import com.comzhichi.model.StudentParent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentParentMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentEmail", source = "student.user.email")
    @Mapping(target = "parentId", source = "parent.id")
    @Mapping(target = "parentPhoneContact", source = "parent.phoneContact")
    StudentParentResponseDTO toResponse(StudentParent studentParent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "parent", ignore = true)
    StudentParent toEntity(StudentParentRequestDTO dto);
}