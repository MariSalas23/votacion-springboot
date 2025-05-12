package com.vote.demo.controller;

import com.vote.demo.model.Gender;
import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.RegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistryControllerTest {

    @InjectMocks
    private RegistryController controller;

    @Mock
    private RegistryService service;

    @Mock
    private Model model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowForm() {
        String view = controller.showForm(model);
        verify(model).addAttribute(eq("person"), any(Person.class));
        assertEquals("form", view);
    }

    @Test
    public void testRegisterValidPerson() {
        Person p = new Person(1L, "Maria Perez", 28, Gender.FEMALE, true);
        when(service.register(p)).thenReturn(RegisterResult.VALID);

        String view = controller.register(p, model);

        verify(model).addAttribute("message", "Registro exitoso. ¡Puede votar!");
        assertEquals("form", view);
    }

    @Test
    public void testRegisterUnderage() {
        Person p = new Person(2L, "Niño Menor", 16, Gender.MALE, true);
        when(service.register(p)).thenReturn(RegisterResult.UNDERAGE);

        String view = controller.register(p, model);

        verify(model).addAttribute("message", "No puede votar: es menor de edad.");
        assertEquals("form", view);
    }

    @Test
    public void testRegisterDeadPerson() {
        Person p = new Person(3L, "Muerto Vivo", 30, Gender.OTHER, false);
        when(service.register(p)).thenReturn(RegisterResult.DEAD);

        String view = controller.register(p, model);

        verify(model).addAttribute("message", "No puede votar: la persona está registrada como fallecida.");
        assertEquals("form", view);
    }

    @Test
    public void testRegisterInvalidPerson() {
        Person p = new Person(4L, "123!Invalid", 20, Gender.MALE, true);
        when(service.register(p)).thenReturn(RegisterResult.INVALID);

        String view = controller.register(p, model);

        verify(model).addAttribute("message", "Datos inválidos. Verifique que todos los campos estén correctamente diligenciados.");
        assertEquals("form", view);
    }
}
