package com.vote.demo.service.rules;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;

public interface ValidationRule {
    RegisterResult validate(Person p);
}