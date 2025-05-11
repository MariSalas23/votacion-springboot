package com.vote.demo.service.rules;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;

public class AgeRule implements ValidationRule {
    @Override
    public RegisterResult validate(Person p) {
        return p.getAge() < 18 ? RegisterResult.UNDERAGE : RegisterResult.VALID;
    }
}
