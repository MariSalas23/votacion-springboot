package com.vote.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.RegistryService;

public class RegistryControllerTest {

    @Mock
    private RegistryService service;

    @InjectMocks
    private RegistryController controller;

    public RegistryControllerTest() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    public void testRegistroValido() {
        Person person = new Person("Mario", 30, Gender.MALE, true);
        when(service.registerVoter(person)).thenReturn(RegisterResult.VALID);

        RegisterResult result = controller.registerVoter(person);

        assertEquals(RegisterResult.VALID, result);
        verify(service).registerVoter(person); // Verifica que el mock fue llamado
    }

    @Test
    public void testRegistroInvalido() {
        Person person = new Person("Lucía", 10, Gender.FEMALE, true);
        when(service.registerVoter(person)).thenReturn(RegisterResult.UNDERAGE);

        RegisterResult result = controller.registerVoter(person);

        assertEquals(RegisterResult.UNDERAGE, result);
        verify(service).registerVoter(person);
    }
}
