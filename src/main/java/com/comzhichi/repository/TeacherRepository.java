package com.comzhichi.repository;

import com.comzhichi.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findBySpecialtyContainingIgnoreCase(String specialty);
    Optional<Teacher> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO teachers (user_id, specialty) VALUES (:userId, :specialty)", nativeQuery = true)
    void insertTeacher(@Param("userId") Long userId, @Param("specialty") String specialty);
}
