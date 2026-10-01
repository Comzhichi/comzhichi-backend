package com.comzhichi.repository;

import com.comzhichi.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Derived Query Method de Spring Data JPA
    List<Teacher> findBySpecialtyContainingIgnoreCase(String specialty);
}