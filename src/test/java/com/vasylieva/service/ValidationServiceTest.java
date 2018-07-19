package com.vasylieva.service;

import com.vasylieva.model.Error;
import com.vasylieva.model.Trade;
import com.vasylieva.model.ValidationResult;
import com.vasylieva.rule.Rule;
import com.vasylieva.service.impl.ValidationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Spy
    private HashSet<Rule> rules;

    @Mock
    private Rule rule1;

    @Mock
    private Rule rule2;

    @Mock
    private Trade trade1;

    @Mock
    private Trade trade2;

    @Captor
    private ArgumentCaptor<Trade> tradeArgumentCaptor;

    @Before
    public void setUp() {
        rules.add(rule1);

    }

    @Test
    public void shouldValidateWithoutErrorsWhenApplicable() {
        when(rule1.isApplicable(trade1)).thenReturn(true);
        when(rule1.apply(trade1)).thenReturn(Optional.empty());

        List<ValidationResult> resultList = validationService.validate(Collections.singletonList(trade1));

        assertThat(resultList.isEmpty(), is(true));
        verify(rule1).isApplicable(trade1);
        verify(rule1).apply(trade1);
    }

    @Test
    public void shouldValidateSeveralTradesWithoutErrorsBySeveralRulesWhenAllApplicable() {
        rules.add(rule2);
        when(rule1.isApplicable(any(Trade.class))).thenReturn(true);
        when(rule2.isApplicable(any(Trade.class))).thenReturn(true);
        when(rule1.apply(any(Trade.class))).thenReturn(Optional.empty());
        when(rule2.apply(any(Trade.class))).thenReturn(Optional.empty());

        List<ValidationResult> resultList = validationService.validate(Arrays.asList(trade1, trade2));

        assertThat(resultList.isEmpty(), is(true));

        verify(rule1, times(2)).isApplicable(tradeArgumentCaptor.capture());
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade1), is(true));
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade2), is(true));

        verify(rule2, times(2)).isApplicable(tradeArgumentCaptor.capture());
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade1), is(true));
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade2), is(true));

        verify(rule1, times(2)).apply(tradeArgumentCaptor.capture());
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade1), is(true));
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade2), is(true));

        verify(rule2, times(2)).apply(tradeArgumentCaptor.capture());
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade1), is(true));
        assertThat(tradeArgumentCaptor.getAllValues().contains(trade2), is(true));
    }

    @Test
    public void shouldValidateWithoutErrorsWhenNotApplicable() {
        when(rule1.isApplicable(trade1)).thenReturn(false);

        List<ValidationResult> resultList = validationService.validate(Collections.singletonList(trade1));

        assertThat(resultList.isEmpty(), is(true));
        verify(rule1).isApplicable(trade1);
        verify(rule1, never()).apply(any(Trade.class));
    }

    @Test
    public void shouldValidateWithError() {
        when(rule1.isApplicable(trade1)).thenReturn(true);
        Error error = new Error("ERROR");
        when(rule1.apply(trade1)).thenReturn(Optional.of(error));

        List<ValidationResult> resultList = validationService.validate(Collections.singletonList(trade1));

        assertThat(resultList.size(), is(1));
        assertThat(resultList.get(0).getErrors().size(), is(1));
        assertThat(resultList.get(0).getErrors().iterator().next(), is(error));
        assertThat(resultList.get(0).getTrade(), is(trade1));
    }

    @Test
    public void shouldValidateWithErrorsWhenSeveralRulesAndSeveralTrades() {
        rules.add(rule2);
        when(rule1.isApplicable(any(Trade.class))).thenReturn(true);
        when(rule2.isApplicable(any(Trade.class))).thenReturn(true);
        Error error1 = new Error("ERROR1");
        Error error2 = new Error("ERROR2");
        when(rule1.apply(trade1)).thenReturn(Optional.of(error1));
        when(rule1.apply(trade2)).thenReturn(Optional.empty());
        when(rule2.apply(trade1)).thenReturn(Optional.empty());
        when(rule2.apply(trade2)).thenReturn(Optional.of(error2));

        List<ValidationResult> resultList = validationService.validate(Arrays.asList(trade1, trade2));

        assertThat(resultList.size(), is(2));
        assertThat(resultList.get(0).getErrors().size(), is(1));
        assertThat(resultList.get(0).getErrors().iterator().next(), is(error1));
        assertThat(resultList.get(0).getTrade(), is(trade1));
        assertThat(resultList.get(1).getErrors().size(), is(1));
        assertThat(resultList.get(1).getErrors().iterator().next(), is(error2));
        assertThat(resultList.get(1).getTrade(), is(trade2));
    }
}
