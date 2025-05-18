package com.vote.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;

import jakarta.annotation.PostConstruct;

@Repository
public class PersonDAO {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @PostConstruct
    public void init() {
        System.out.println("🔗 Conectando a: " + url);
        System.out.println("👤 Usuario DB: " + user);
    }

    public void insert(Person person) {
        String sql = "INSERT INTO person (id, name, age, gender, alive) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, person.getId());
            pstmt.setString(2, person.getName());
            pstmt.setInt(3, person.getAge());
            pstmt.setString(4, person.getGender().toString());
            pstmt.setBoolean(5, person.getAlive());
            pstmt.executeUpdate();
            System.out.println("✅ Persona insertada: " + person.getId());

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar persona:");
            e.printStackTrace();
        }
    }

    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Person person = new Person();
                person.setId(rs.getLong("id"));
                person.setName(rs.getString("name"));
                person.setAge(rs.getInt("age"));
                person.setGender(Gender.valueOf(rs.getString("gender")));
                person.setAlive(rs.getBoolean("alive"));
                people.add(person);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener personas:");
            e.printStackTrace();
        }

        return people;
    }

    public boolean existsById(long id) {
        String sql = "SELECT id FROM person WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("❌ Error al verificar ID:");
            e.printStackTrace();
            return false;
        }
    }
}
