package com.vasylieva.rule.impl;

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

public class CounterpartySupportedRuleTest {

    private CounterpartySupportedRule rule = new CounterpartySupportedRule();

    @DisplayName("Should create error when the customer not supported")
    @ParameterizedTest(name = "{index} => customer={0}, error created={1}")
    @CsvSource({
            "PLUTO1, false",
            "PLUTO2, false",
            "NOT_SUPPORTED, true",
            "null, true"
    })
    void shouldValidateCounterparty(String customer, boolean error) {
        Trade trade = mock(Trade.class);
        when(trade.getCustomer()).thenReturn(customer);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @Test
    public void shouldBeApplicableForAllTrades() {
        assertThat(rule.isApplicable(mock(Trade.class)), is(true));
    }
}
