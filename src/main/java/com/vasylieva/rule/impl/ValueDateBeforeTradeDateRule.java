package com.vasylieva.rule.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ValueDateBeforeTradeDateRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = Objects.nonNull(trade.getValueDate()) && valueDateBeforeTrade(trade) ?
                new Error("Value date is before Trade date")
                : null;

        return Optional.ofNullable(error);

    }

    @Override
    public boolean isApplicable(Trade trade) {
        return true;
    }

    private boolean valueDateBeforeTrade(Trade trade) {
        return trade.getValueDate().isBefore(trade.getTradeDate());
    }
}
