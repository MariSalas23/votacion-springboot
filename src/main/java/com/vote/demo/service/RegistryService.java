package com.vote.demo.service;

import com.vote.demo.dao.PersonDAO;
import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistryService {

    private final PersonDAO dao;

    public RegistryService(PersonDAO dao) {
        this.dao = dao;
    }

    public RegisterResult register(Person p) {
        if (p.getName() == null || p.getName().isBlank() || !p.getName().matches("[A-Za-z ]+")) return RegisterResult.INVALID;
        if (p.getAge() < 18) return RegisterResult.UNDERAGE;
        if (p.getAlive() == null || !p.getAlive()) return RegisterResult.DEAD;
        if (dao.existsById(p.getId())) return RegisterResult.INVALID;

        dao.insert(p);
        return RegisterResult.VALID;
    }

    public List<Person> getAllPeople() {
        return dao.getAll();
    }
}
