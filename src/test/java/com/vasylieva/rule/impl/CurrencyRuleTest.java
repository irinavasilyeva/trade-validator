package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyRuleTest {

    private CurrencyRule rule = new CurrencyRule();

    @DisplayName("Should create error when CcyPair currencies are not valid ISO codes ")
    @ParameterizedTest(name = "{index} => currencyPair={0}, error created={1}")
    @CsvSource({
            "EURUSD, false",
            "EURPPP, true",
            "PPPUSD, true",
            "null, true"
    })
    void shouldValidateCcyPairCurrency(String currencyPair, boolean error) {
        Trade trade = mock(Trade.class);
        when(trade.getCcyPair()).thenReturn(currencyPair);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @DisplayName("Should create error when PremiumCcy is not valid ISO code")
    @ParameterizedTest(name = "{index} => PremiumCcy={0}, error created={1}")
    @CsvSource({
            "EUR, false",
            "PPP, true",
            "null, true"
    })
    void shouldValidatePremiumCcyCurrency(String currency, boolean error) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getPremiumCcy()).thenReturn(currency);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @DisplayName("Should create error when PayCcy is not valid ISO code")
    @ParameterizedTest(name = "{index} => PayCcy={0}, error created={1}")
    @CsvSource({
            "EUR, false",
            "PPP, true",
            "null, true"
    })
    void shouldValidatePayCcyCurrency(String currency, boolean error) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getPayCcy()).thenReturn(currency);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @Test
    public void shouldPointAllTheErrorFieldsInMessage() {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getCcyPair()).thenReturn("PPPVVV");
        when(trade.getPremiumCcy()).thenReturn("PPP");
        when(trade.getPayCcy()).thenReturn("VVV");

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getMessage().contains("CcyPair"), is(true));
        assertThat(result.get().getMessage().contains("PremiumCcy"), is(true));
        assertThat(result.get().getMessage().contains("PayCcy"), is(true));
    }

    @Test
    public void shouldBeApplicableForAllTrades() {
        assertThat(rule.isApplicable(mock(Trade.class)), is(true));
    }
}
