package com.vasylieva.rule.impl;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.mock.CurrentDateHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ForwardValueDateRuleTest {
    private static final LocalDate CURRENT_DATE = LocalDate.of(2016, 10, 9);

    @InjectMocks
    private ForwardValueDateRule testRule;

    @Mock
    private CurrentDateHandler currentDateHandler;

    @Before
    public void setUp() {
        when(currentDateHandler.getCurrentDate()).thenReturn(CURRENT_DATE);
    }

    @Test
    public void shouldValidateForwardValueDateWithoutErrorIfMoreThanIn2Days() {
        Trade trade = createTradeWithValueDate(LocalDate.of(2018, 10, 9));

        Optional<Error> result = testRule.apply(trade);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void shouldValidateForwardValueDateWithErrorIfInThePast() {
        Trade trade = createTradeWithValueDate(LocalDate.of(2015, 10, 9));

        Optional<Error> result = testRule.apply(trade);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldValidateForwardValueDateWithErrorIfDatesEqual() {
        Trade trade = createTradeWithValueDate(LocalDate.of(2016, 10, 9));

        Optional<Error> result = testRule.apply(trade);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldValidateForwardValueDateWithErrorIfLikeSpot() {
        Trade trade = createTradeWithValueDate(LocalDate.of(2016, 10, 11));

        Optional<Error> result = testRule.apply(trade);

        assertThat(result.isPresent(), is(true));
    }

    @Test
    public void shouldCheckIsApplicableIfForward() {
        Trade trade = mock(Trade.class);
        when(trade.getType()).thenReturn(TradeType.Forward);

        boolean result = testRule.isApplicable(trade);

        assertThat(result, is(true));
    }

    @Test
    public void shouldCheckNotApplicableIfNotForward() {
        Trade trade = mock(Trade.class);
        when(trade.getType()).thenReturn(TradeType.Spot);

        boolean result = testRule.isApplicable(trade);

        assertThat(result, is(false));
    }

    private Trade createTradeWithValueDate(LocalDate date) {
        Trade trade = mock(Trade.class);
        when(trade.getValueDate()).thenReturn(date);
        return trade;
    }
}
