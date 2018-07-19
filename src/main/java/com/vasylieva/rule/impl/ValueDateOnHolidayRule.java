package com.vasylieva.rule.impl;

import com.vasylieva.mock.HolidayForCurrencyHandler;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ValueDateOnHolidayRule implements Rule {

    @Autowired
    private HolidayForCurrencyHandler handler;

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = isValueDatePresent(trade) && isHolidayForCurrency(trade) ?
                new Error("Value date falls on holiday for currency")
                : null;

        return Optional.ofNullable(error);
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return true;
    }

    private boolean isValueDatePresent(Trade trade) {
        return Objects.nonNull(trade.getValueDate());
    }

    private boolean isHolidayForCurrency(Trade trade) {
        if (Objects.nonNull(trade.getCcyPair())) {
            return handler.findHolidaysForCurrency(trade.getCcyPair().trim().substring(0, 3))
                    .contains(trade.getValueDate())
            || handler.findHolidaysForCurrency(trade.getCcyPair().trim().substring(3))
                    .contains(trade.getValueDate());
        }

        return false;
    }
}
