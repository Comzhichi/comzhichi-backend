package com.comzhichi.mapper;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    StudentResponseDTO toResponse(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Student toEntity(StudentRequestDTO dto);
}