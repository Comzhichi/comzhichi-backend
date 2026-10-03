package com.comzhichi.mapper;

import com.comzhichi.dto.request.EnrollmentRequestDTO;
import com.comzhichi.dto.response.EnrollmentResponseDTO;
import com.comzhichi.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface EnrollmentMapper {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentEmail", source = "student.email")
    @Mapping(target = "sectionId", source = "section.id")
    @Mapping(target = "sectionGroupCode", source = "section.groupCode")
    @Mapping(target = "courseName", source = "section.course.name")
    EnrollmentResponseDTO toResponse(Enrollment enrollment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "section", ignore = true)
    Enrollment toEntity(EnrollmentRequestDTO dto);
}
