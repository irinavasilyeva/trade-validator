package com.vasylieva.rule.impl;

import com.vasylieva.model.BaseOption;
import com.vasylieva.model.Error;
import com.vasylieva.model.type.TradeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OptionStyleRuleTest {

    private OptionStyleRule rule = new OptionStyleRule();

    @DisplayName("Should create error when the style is not American or European")
    @ParameterizedTest(name = "{index} => style={0}, error created={1}")
    @CsvSource({
            "AMERICAN, false",
            "EUROPEAN, false",
            "NOT_SUPPORTED, true"
    })
    void shouldValidateOptionStyle(String style, boolean error) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getStyle()).thenReturn(style);

        Optional<Error> result = rule.apply(trade);

        assertThat(result.isPresent(), is(error));
    }

    @DisplayName("Should check if applicable")
    @ParameterizedTest(name = "{index} => type={0}, result={2}")
    @CsvSource({
            "VanillaOption, true",
            "Spot, false"
    })
    void shouldValidateIsApplicable(String type, boolean result) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getType()).thenReturn(TradeType.valueOf(type));

        boolean isApplicable = rule.isApplicable(trade);

        assertThat(isApplicable, is(result));
    }
}
