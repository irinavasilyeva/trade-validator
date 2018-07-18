package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.rule.Rule;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CurrencyRule implements Rule {

    @Override
    public Optional<Error> apply(Trade trade) {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        String fieldsStr = findAllCurrencies(trade).entrySet().stream()
                .filter(curr -> !isCurrencyCorrect(curr.getValue(), currencies))
                .map(curr -> curr.getKey())
                .reduce("", (f1, f2) -> f1 + ", " + f2);

         return Optional.ofNullable(
                 fieldsStr.isEmpty() ? null : new Error("Currency is not valid in field: " + fieldsStr)
         );
    }

    private Map<String, String> findAllCurrencies(Trade trade) {
        Map<String, String> currencies = new HashMap<>();
        if (Objects.nonNull(trade.getCcyPair())) {
            currencies.put("CcyPair", trade.getCcyPair().trim());
        }

        if (trade instanceof BaseOption
                && Objects.nonNull(((BaseOption) trade).getPremiumCcy())) {
            currencies.put("PremiumCcy", trade.getCcyPair().trim());
        }

        return currencies;
    }

    private boolean isCurrencyCorrect(String curr, Set<Currency> currencies) {
        return currencies.stream()
                .map(Currency::getCurrencyCode)
                .anyMatch(found -> found.equalsIgnoreCase(curr));
    }
}
