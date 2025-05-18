package com.vote.demo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/votacion");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "votante");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "secret123");

    @PostConstruct
    public void init() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS person (
                    id BIGINT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    age INTEGER NOT NULL,
                    gender VARCHAR(10) NOT NULL,
                    alive BOOLEAN NOT NULL
                );
            """;
            stmt.execute(sql);
            System.out.println("✅ Tabla 'person' creada/verificada en PostgreSQL");

        } catch (Exception e) {
            System.err.println("❌ Error creando/verificando tabla en PostgreSQL:");
            e.printStackTrace();
        }
    }
}
