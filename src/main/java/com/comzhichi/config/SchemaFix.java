package com.comzhichi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaFix {

    private final SchemaFixService schemaFixService;

    public SchemaFix(SchemaFixService schemaFixService) {
        this.schemaFixService = schemaFixService;
    }

    @Bean
    public CommandLineRunner fixTeacherActiveColumn() {
        return args -> schemaFixService.fixTeacherActiveColumn();
    }
}
