package com.comzhichi.mapper;

import com.comzhichi.dto.request.CourseRequestDTO;
import com.comzhichi.dto.response.CourseResponseDTO;
import com.comzhichi.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "coordinatorId", source = "coordinator.id")
    @Mapping(target = "coordinatorDepartment", source = "coordinator.department")
    CourseResponseDTO toResponse(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coordinator", ignore = true)
    Course toEntity(CourseRequestDTO dto);
}