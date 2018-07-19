package com.vasylieva.mock;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CurrentDateHandler {
    private static final LocalDate CURRENT_DATE = LocalDate.of(2016, 10, 9);

    public LocalDate getCurrentDate() {
        return CURRENT_DATE;
    }
}
