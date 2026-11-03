package com.comzhichi.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchemaFixService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void fixTeacherActiveColumn() {
        try {
            entityManager.createNativeQuery(
                    "ALTER TABLE teachers ADD COLUMN IF NOT EXISTS active BOOLEAN DEFAULT TRUE"
            ).executeUpdate();
            entityManager.createNativeQuery(
                    "UPDATE teachers SET active = TRUE WHERE active IS NULL"
            ).executeUpdate();
            System.out.println("SchemaFix: columna 'active' verificada/creada en teachers");
        } catch (Exception e) {
            System.err.println("SchemaFix error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
