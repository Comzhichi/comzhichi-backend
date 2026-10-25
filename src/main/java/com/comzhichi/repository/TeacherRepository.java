package com.comzhichi.repository;

import com.comzhichi.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // Derived Query Method de Spring Data JPA
    List<Teacher> findBySpecialtyContainingIgnoreCase(String specialty);
    Optional<Teacher> findByUser_Id(Long userId);
}