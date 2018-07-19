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

public class ValueDateOnWeekendRuleTest {

    private ValueDateOnWeekendRule rule = new ValueDateOnWeekendRule();

    @DisplayName("Should create error when the value date is on weekend ")
    @ParameterizedTest(name = "{index} => valueDate={0}, error created={1}")
    @CsvSource({
            "2018-07-19, false",
            "2018-07-21, true"
    })
    void shouldValidateValueDate(LocalDate valueDate, boolean error) {
        Trade trade = mock(Trade.class);
        when(trade.getValueDate()).thenReturn(valueDate);

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
