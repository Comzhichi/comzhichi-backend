package com.comzhichi.repository;

import com.comzhichi.model.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    List<Coordinator> findByDepartmentContainingIgnoreCase(String department);
    Optional<Coordinator> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO coordinators (user_id, department) VALUES (:userId, :department)", nativeQuery = true)
    void insertCoordinator(@Param("userId") Long userId, @Param("department") String department);
}
