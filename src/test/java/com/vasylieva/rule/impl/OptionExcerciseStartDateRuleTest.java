package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.type.TradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OptionExcerciseStartDateRuleTest {

    private OptionExcerciseStartDateRule rule = new OptionExcerciseStartDateRule();

    @DisplayName("Should create error when the ExcerciseStartDate is not after the trade date " +
            "or not before the expiry date")
    @ParameterizedTest(name = "{index} => excerciseStartDate={0}, " +
            "expiryDate={1}, " +
            "tradeDate={2}, " +
            "error created={3}")
    @CsvSource({
            "2017-01-01, 2018-01-01, 2016-01-01, false",
            "2017-01-01, 2018-01-01, 2018-01-01, true",
            "2017-01-01, 2016-01-01, 2018-01-01, true",
            "2017-01-01, 2017-01-01, 2016-01-01, true",
            "2017-01-01, 2018-01-01, 2017-01-01, true"
    })
    void shouldValidateExcerciseStartDate(LocalDate excerciseStartDate,
                                    LocalDate expiryDate,
                                    LocalDate tradeDate,
                                    boolean error) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getExcerciseStartDate()).thenReturn(excerciseStartDate);
        when(trade.getExpiryDate()).thenReturn(expiryDate);
        when(trade.getTradeDate()).thenReturn(tradeDate);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @DisplayName("Should check if applicable")
    @ParameterizedTest(name = "{index} => type={0}, style={1}, result={2}")
    @CsvSource({
            "VanillaOption, AMERICAN, true",
            "VanillaOption, EUROPEAN, true",
            "VanillaOption, ELSE, false",
            "Spot, AMERICAN, false"
    })
    void shouldValidateIsApplicable(String type, String style, boolean result) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getType()).thenReturn(TradeType.valueOf(type));
        when(trade.getStyle()).thenReturn(style);

        boolean isApplicable = rule.isApplicable(trade);

        assertThat(isApplicable, is(result));
    }
}
