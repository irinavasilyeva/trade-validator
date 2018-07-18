package com.vasylieva.rule.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.CustomerType;
import com.vasylieva.rule.Rule;

import java.util.Arrays;
import java.util.Optional;

public class CounterpartySupportedRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = isCustomerSupported(trade.getCustomer()) ? null
                : new Error("Counterparty is not supported");

        return Optional.ofNullable(error);
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return true;
    }

    private boolean isCustomerSupported(String customer) {
        return Arrays.stream(CustomerType.values()).anyMatch(c -> c.name().equalsIgnoreCase(customer));
    }
}
