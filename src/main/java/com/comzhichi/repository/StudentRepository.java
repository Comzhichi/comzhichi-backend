package com.comzhichi.repository;

import com.comzhichi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByLevelContainingIgnoreCase(String level);

    Optional<Student> findByUser_Id(Long userId);
}