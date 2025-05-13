package com.vote.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.RegistryService;

@Controller
public class RegistryController {

    @Autowired
    private RegistryService service;

    @PostMapping("/register")
    public String register(@ModelAttribute Person person, Model model) {
        RegisterResult result = service.register(person);

        String message = switch (result) {
            case VALID -> "Registro exitoso. ¡Puede votar!";
            case UNDERAGE -> "No puede votar: es menor de edad.";
            case DEAD -> "No puede votar: la persona está registrada como fallecida.";
            default -> "Datos inválidos. Verifique que todos los campos estén correctamente diligenciados.";
        };

        model.addAttribute("message", message);
        model.addAttribute("person", new Person()); // <- limpia el formulario
        return "form";
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("person", new Person());
        return "form";
    }

    // NUEVO: ver personas guardadas como JSON
    @GetMapping("/personas")
    @ResponseBody
    public List<Person> listAllPeople() {
        return service.getAllPeople();
    }
}
