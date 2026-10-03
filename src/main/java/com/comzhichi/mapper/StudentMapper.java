package com.comzhichi.mapper;

import com.comzhichi.dto.request.StudentRequestDTO;
import com.comzhichi.dto.response.StudentResponseDTO;
import com.comzhichi.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface StudentMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userEmail", source = "email")
    StudentResponseDTO toResponse(Student student);

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequestDTO dto);
}
