package com.vote.demo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;

@Repository
public class PersonDAO {

    private final String url = "jdbc:sqlite:personas.db";

    public void insert(Person person) {
        String sql = "INSERT INTO person (id, name, age, gender, alive) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, person.getId());
            pstmt.setString(2, person.getName());
            pstmt.setInt(3, person.getAge());
            pstmt.setString(4, person.getGender().toString());
            pstmt.setBoolean(5, person.getAlive());
            System.out.println("Guardando persona: " + person.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("ERROR AL GUARDAR PERSONA:");
            e.printStackTrace();
        }
    }

    public List<Person> getAll() {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DriverManager.getConnection(url);
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
            e.printStackTrace();
        }

        return people;
    }

    public boolean existsById(long id) {
        String sql = "SELECT id FROM person WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}