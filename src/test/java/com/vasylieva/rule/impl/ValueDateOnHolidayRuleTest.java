package com.vasylieva.rule.impl;

import com.vasylieva.mock.HolidayForCurrencyHandler;
import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateOnHolidayRuleTest {

    @InjectMocks
    private ValueDateOnHolidayRule rule;

    @Mock
    private HolidayForCurrencyHandler handler;

    @Test
    public void shouldCreateErrorWhenHolidayForFirstCurrencyInPair() {
        LocalDate dt = LocalDate.of(2018, 1, 1);
        when(handler.findHolidaysForCurrency("RUS")).thenReturn(Collections.singletonList(dt));
        Trade trade = createTrade(dt, "RUSUSD");

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldCreateErrorWhenHolidayForSecondCurrencyInPair() {
        LocalDate dt = LocalDate.of(2018, 7, 4);
        when(handler.findHolidaysForCurrency("RUS")).thenReturn(Collections.emptyList());
        when(handler.findHolidaysForCurrency("USD")).thenReturn(Collections.singletonList(dt));
        Trade trade = createTrade(dt, "RUSUSD");

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldNotCreateErrorWhenNoHolidayForAnyCurrency() {
        LocalDate dt = LocalDate.of(2018, 7, 4);
        when(handler.findHolidaysForCurrency("RUS")).thenReturn(Collections.emptyList());
        when(handler.findHolidaysForCurrency("USD")).thenReturn(Collections.emptyList());
        Trade trade = createTrade(dt, "RUSUSD");

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(false));
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

    private Trade createTrade(LocalDate valueDate, String currencyPair) {
        Trade trade = mock(Trade.class);
        when(trade.getValueDate()).thenReturn(valueDate);
        when(trade.getCcyPair()).thenReturn(currencyPair);
        return trade;
    }
}
