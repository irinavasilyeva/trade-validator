package com.vasylieva.service.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.ValidationResult;
import com.vasylieva.rule.Rule;
import com.vasylieva.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private Set<Rule> rules;

    @Override
    public List<ValidationResult> validate(List<Trade> trades) {
        return trades.stream()
                .map(this::validateToResult)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ValidationResult> validateToResult(Trade trade) {
        Set<Error> errors = rules.stream()
                .filter(rule -> rule.isApplicable(trade))
                .map(rule -> rule.apply(trade))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        return Optional.ofNullable(errors.isEmpty() ? null
                : new ValidationResult(trade, errors));
    }
}
