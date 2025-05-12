package com.vote.demo.service;

import com.vote.demo.dao.PersonDAO;
import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FakePersonDAO extends PersonDAO {
    private final Map<Long, Person> people = new HashMap<>();

    @Override
    public void insert(Person person) {
        people.put(person.getId(), person);
    }

    @Override
    public boolean existsById(long id) {
        return people.containsKey(id);
    }

    @Override
    public List<Person> getAll() {
        return new ArrayList<>(people.values());
    }

    public void clear() {
        people.clear();
    }
}

public class RegistryServiceTest {

    private RegistryService registryService;
    private FakePersonDAO fakeDao;

    @BeforeEach
    public void setup() {
        fakeDao = new FakePersonDAO();
        registryService = new RegistryService(fakeDao);
    }

    @Test
    public void testRegisterValidPerson() {
        Person p = new Person(1L, "Pedro Gomez", 30, Gender.MALE, true);
        RegisterResult result = registryService.register(p);

        assertEquals(RegisterResult.VALID, result);
        assertTrue(fakeDao.existsById(1L));
    }

    @Test
    public void testInvalidName() {
        Person p = new Person(2L, "Ana123", 25, Gender.FEMALE, true);
        RegisterResult result = registryService.register(p);

        assertEquals(RegisterResult.INVALID, result);
        assertFalse(fakeDao.existsById(2L));
    }

    @Test
    void testUnderagePerson() {
        Person p = new Person(100L, "Pedro Perez", 17, Gender.MALE, true);
        RegisterResult result = registryService.register(p);
        assertEquals(RegisterResult.UNDERAGE, result);
    }


    @Test
    public void testDeadPerson() {
        Person p = new Person(4L, "Laura RIP", 45, Gender.FEMALE, false);
        RegisterResult result = registryService.register(p);

        assertEquals(RegisterResult.DEAD, result);
        assertFalse(fakeDao.existsById(4L));
    }

    @Test
    public void testDuplicateId() {
        Person p1 = new Person(5L, "Carlos Uno", 40, Gender.MALE, true);
        Person p2 = new Person(5L, "Carlos Dos", 40, Gender.MALE, true);
        registryService.register(p1);
        RegisterResult result = registryService.register(p2);

        assertEquals(RegisterResult.INVALID, result);
        assertEquals(1, fakeDao.getAll().size());
    }
}
