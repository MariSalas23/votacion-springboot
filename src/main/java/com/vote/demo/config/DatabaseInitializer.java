package com.vote.demo.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseInitializer {

    private static final String URL = "jdbc:sqlite:personas.db";

    @PostConstruct
    public void init() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS person (
                    id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    age INTEGER NOT NULL,
                    gender TEXT NOT NULL,
                    alive BOOLEAN NOT NULL
                );
            """;
            stmt.execute(sql);
            System.out.println("✅ Tabla 'person' verificada o creada en votantes.db");

        } catch (Exception e) {
            System.err.println("❌ Error creando/verificando la tabla:");
            e.printStackTrace();
        }
    }
}
