package com.vasylieva.mock;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class HolidayForCurrencyHandler {

    private Map<String, List<LocalDate>> holidays;

    public HolidayForCurrencyHandler() {
        holidays = new HashMap<>();
        holidays.put("USD", Arrays.asList(
                //just for prototype
                LocalDate.of(2016, 1, 15),
                LocalDate.of(2016, 5, 28),
                LocalDate.of(2016, 7, 4)
        ));
    }

    public List<LocalDate> findHolidaysForCurrency(String currency) {
        return holidays.getOrDefault(currency, new ArrayList<>());
    }
}
