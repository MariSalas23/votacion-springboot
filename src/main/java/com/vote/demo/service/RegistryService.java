package com.vote.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vote.demo.model.Person;
import com.vote.demo.model.RegisterResult;
import com.vote.demo.service.rules.AgeRule;
import com.vote.demo.service.rules.AliveRule;
import com.vote.demo.service.rules.ValidationRule;

@Service
public class RegistryService {

    private final List<ValidationRule> rules = Arrays.asList(
            new AliveRule(),
            new AgeRule()
    );

    public RegisterResult registerVoter(Person p) {
        for (ValidationRule rule : rules) {
            RegisterResult result = rule.validate(p);
            if (result != RegisterResult.VALID) {
                return result;
            }
        }
        return RegisterResult.VALID;
    }
}
