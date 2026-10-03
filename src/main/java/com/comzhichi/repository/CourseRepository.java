package com.comzhichi.repository;

import com.comzhichi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByLevelContainingIgnoreCase(String level);
    List<Course> findByCoordinatorId(Long coordinatorId);
}
