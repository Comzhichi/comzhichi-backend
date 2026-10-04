package com.comzhichi.repository;

import com.comzhichi.model.StudentParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentParentRepository extends JpaRepository<StudentParent, Long> {
    List<StudentParent> findByStudentId(Long studentId);
    List<StudentParent> findByParentId(Long parentId);
}