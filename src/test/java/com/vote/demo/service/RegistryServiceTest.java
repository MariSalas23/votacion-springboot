package com.vote.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;

public class RegistryServiceTest {

    private final RegistryService service = new RegistryService();

    @Test
    public void testVotanteValido() {
        Person p = new Person("Laura", 25, Gender.FEMALE, true);
        RegisterResult result = service.registerVoter(p);
        assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void testMenorDeEdad() {
        Person p = new Person("Carlos", 15, Gender.MALE, true);
        RegisterResult result = service.registerVoter(p);
        assertEquals(RegisterResult.UNDERAGE, result);
    }

    @Test
    public void testPersonaMuerta() {
        Person p = new Person("Ana", 45, Gender.FEMALE, false);
        RegisterResult result = service.registerVoter(p);
        assertEquals(RegisterResult.DEAD, result);
    }

    @Test
    public void testMuertoYMenor() {
        Person p = new Person("Juan", 16, Gender.MALE, false);
        RegisterResult result = service.registerVoter(p);
        assertEquals(RegisterResult.DEAD, result); // Se prioriza estado muerto
    }
}
