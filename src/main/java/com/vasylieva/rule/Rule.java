package com.vasylieva.rule;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;

import java.util.Optional;

public interface Rule {
    Optional<Error> apply(Trade trade);

    boolean isApplicable(Trade trade);
}
