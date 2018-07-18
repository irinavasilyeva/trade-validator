package com.vasylieva.rule.impl;

import com.vasylieva.prototypeStub.CurrentDateHandler;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ForwardValueDateRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        return Optional.ofNullable(
                checkValueDateForForward(trade) ? null : new Error("Forward value date is not correct")
        );
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return TradeType.Forward == trade.getType();
    }

    private boolean checkValueDateForForward(Trade trade) {
        return trade.getValueDate().isAfter(CurrentDateHandler.CURRENT_DATE.plusDays(2));
    }
}
