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

public class OptionExpiryPremiumDateRuleTest {

    private OptionExpiryPremiumDateRule rule = new OptionExpiryPremiumDateRule();

    @DisplayName("Should create error when the expiry date and premium date are not before delivery date")
    @ParameterizedTest(name = "{index} => expiryDate={0}, " +
            "premiumDate={1}, " +
            "deliveryDate={2}, " +
            "error created={3}")
    @CsvSource({
            "2017-01-01, 2017-01-01, 2018-01-01, false",
            "2017-01-01, 2018-01-01, 2018-01-01, true",
            "2018-01-01, 2017-01-01, 2018-01-01, true",
            "2019-01-01, 2017-01-01, 2018-01-01, true",
            "2017-01-01, 2019-01-01, 2018-01-01, true"
    })
    void shouldValidateOptionsDates(LocalDate expiryDate,
                                    LocalDate premiumDate,
                                    LocalDate deliveryDate,
                                    boolean error) {
        BaseOption trade = mock(BaseOption.class);
        when(trade.getExpiryDate()).thenReturn(expiryDate);
        when(trade.getPremiumDate()).thenReturn(premiumDate);
        when(trade.getDeliveryDate()).thenReturn(deliveryDate);

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
