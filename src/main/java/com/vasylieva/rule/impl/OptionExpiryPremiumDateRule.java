package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OptionExpiryPremiumDateRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Error error = checkExpiryPremiumDate((BaseOption) trade) ? null
                : new Error("The expiry date and premium date shall be before delivery date");

        return Optional.ofNullable(error);
    }

    @Override
    public boolean isApplicable(Trade trade) {
        return TradeType.VanillaOption == trade.getType();
    }

    private boolean checkExpiryPremiumDate(BaseOption option) {
        return option.getDeliveryDate().isAfter(option.getExpiryDate())
                && option.getDeliveryDate().isAfter(option.getPremiumDate());
    }
}
