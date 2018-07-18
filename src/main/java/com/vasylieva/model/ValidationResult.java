package com.vasylieva.model;

import java.util.Set;

public class ValidationResult {
    private Trade trade;
    private Set<Error> errors;

    public ValidationResult(Trade trade, Set<Error> errors) {
        this.trade = trade;
        this.errors = errors;
    }

    public Trade getTrade() {
        return trade;
    }

    public Set<Error> getErrors() {
        return errors;
    }
}
