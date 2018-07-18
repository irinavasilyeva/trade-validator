package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.OptionStyle;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class OptionStyleRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        BaseOption option = (BaseOption) trade;
        Error error = checkStyle(option.getStyle()) ?
                new Error("The style can be either American or European") : null;

        return Optional.ofNullable(error);
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return TradeType.VanillaOption == trade.getType();
    }

    private boolean checkStyle(String style) {
        return !Arrays.asList(
                OptionStyle.AMERICAN.name(),
                OptionStyle.EUROPEAN.name()).contains(style);
    }
}
