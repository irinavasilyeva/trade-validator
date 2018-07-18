package com.vasylieva.rule.impl;

import com.vasylieva.CurrentDateHandler;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpotValueDateRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        return Optional.ofNullable(
                checkValueDateForSpot(trade) ? null : new Error("Spot value date is not correct")
        );
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return TradeType.Spot == trade.getType();
    }

    private boolean checkValueDateForSpot(Trade trade) {
        return CurrentDateHandler.CURRENT_DATE.plusDays(2).isEqual(trade.getValueDate());
    }
}
