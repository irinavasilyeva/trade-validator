package com.vasylieva.service;

import java.time.LocalDate;
import java.util.Locale;

public interface HolidayFinderService {
    boolean isDateAHoliday(LocalDate date, Locale locale);
}
