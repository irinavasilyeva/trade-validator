package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.OptionStyle;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class OptionExcerciseStartDateRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = checkExcerciseStartDate((BaseOption) trade) ? null
                : new Error("The excercise start date has to be after the trade date but before the expiry date");

        return Optional.ofNullable(error);
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return TradeType.VanillaOption == trade.getType()
                && OptionStyle.valueOf(((BaseOption) trade).getStyle()) == OptionStyle.AMERICAN;
    }

    private boolean checkExcerciseStartDate(BaseOption option) {
        return Objects.nonNull(option.getExcerciseStartDate())
                && option.getExcerciseStartDate().isBefore(option.getExpiryDate())
                && option.getExcerciseStartDate().isAfter(option.getTradeDate());
    }
}
