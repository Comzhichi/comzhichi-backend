package com.comzhichi.repository;

import com.comzhichi.model.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    List<Coordinator> findByDepartmentContainingIgnoreCase(String department);
}