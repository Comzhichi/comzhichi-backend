package com.comzhichi.repository;

import com.comzhichi.model.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    List<Coordinator> findByDepartmentContainingIgnoreCase(String department);
    Optional<Coordinator> findByUser_Id(Long userId);
}