package com.vote.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.RegistryService;

@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Autowired
    private RegistryService service;

    @PostMapping("/register")
    public RegisterResult registerVoter(@RequestBody Person person) {
        return service.registerVoter(person);
    }
    
}