package com.vasylieva.rule.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class ValueDateOnWeekendRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = isValueDatePresent(trade) && isDateWeekend(trade.getValueDate()) ?
                new Error("Value date falls on weekend")
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

    private boolean isDateWeekend(LocalDate date) {
        return Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
                .contains(date.getDayOfWeek());
    }
}
