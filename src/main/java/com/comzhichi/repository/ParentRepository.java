package com.comzhichi.repository;

import com.comzhichi.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> findByPhoneContact(String phoneContact);
    Optional<Parent> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO parents (user_id, phone_contact) VALUES (:userId, :phoneContact)", nativeQuery = true)
    void insertParent(@Param("userId") Long userId, @Param("phoneContact") String phoneContact);
}
