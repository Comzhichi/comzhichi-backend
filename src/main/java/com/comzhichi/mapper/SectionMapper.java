package com.comzhichi.mapper;

import com.comzhichi.dto.request.SectionRequestDTO;
import com.comzhichi.dto.response.SectionResponseDTO;
import com.comzhichi.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "courseLevel", source = "course.level")
    @Mapping(target = "courseDescription", source = "course.description")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "teacherSpecialty", source = "teacher.specialty")
    @Mapping(target = "teacherName", source = "teacher.user.fullName")
    SectionResponseDTO toResponse(Section section);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    Section toEntity(SectionRequestDTO dto);
}