package com.vasylieva.rule.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValueDateBeforeTradeDateRuleTest {

    private ValueDateBeforeTradeDateRule rule = new ValueDateBeforeTradeDateRule();

    @DisplayName("Should create error when the value date is before trade date ")
    @ParameterizedTest(name = "{index} => valueDate={0}, tradeDate={1}, error created={2}")
    @CsvSource({
            "2017-01-01, 2016-01-01, false",
            "2017-01-01, 2018-01-01, true",
            "2017-01-01, 2017-01-01, false"
    })
    void shouldValidateValueDate(LocalDate valueDate, LocalDate tradeDate, boolean error) {
        Trade trade = mock(Trade.class);
        when(trade.getValueDate()).thenReturn(valueDate);
        when(trade.getTradeDate()).thenReturn(tradeDate);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @Test
    public void shouldNotCreateErrorWhenNoValueDate() {
        Trade trade = mock(Trade.class);
        when(trade.getValueDate()).thenReturn(null);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void shouldBeApplicableForAllTrades() {
        assertThat(rule.isApplicable(mock(Trade.class)), is(true));
    }
}
