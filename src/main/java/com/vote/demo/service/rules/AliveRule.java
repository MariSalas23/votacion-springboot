package com.vote.demo.service.rules;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;

public class AliveRule implements ValidationRule {
    @Override
    public RegisterResult validate(Person p) {
        return !p.isAlive() ? RegisterResult.DEAD : RegisterResult.VALID;
    }
}