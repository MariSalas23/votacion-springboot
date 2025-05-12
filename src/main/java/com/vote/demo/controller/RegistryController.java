package com.vote.demo.controller;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegistryController {

    @Autowired
    private RegistryService service;

    @PostMapping("/register")
    public String register(@ModelAttribute Person person, Model model) {
        RegisterResult result = service.register(person);

        String message;
        switch (result) {
            case VALID:
                message = "Registro exitoso. ¡Puede votar!";
                break;
            case UNDERAGE:
                message = "No puede votar: es menor de edad.";
                break;
            case DEAD:
                message = "No puede votar: la persona está registrada como fallecida.";
                break;
            case INVALID:
            default:
                message = "Datos inválidos. Verifique que todos los campos estén correctamente diligenciados.";
        }

        model.addAttribute("message", message);
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
