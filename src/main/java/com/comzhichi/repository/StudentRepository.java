package com.comzhichi.repository;

import com.comzhichi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByLevelContainingIgnoreCase(String level);
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO students (user_id, level, institution) VALUES (:userId, :level, :institution)", nativeQuery = true)
    void insertStudent(@Param("userId") Long userId, @Param("level") String level, @Param("institution") String institution);
}
