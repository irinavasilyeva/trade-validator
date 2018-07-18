package com.vasylieva.service.impl;

import com.vasylieva.service.HolidayFinderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;

@Service
public class HolidayFinderServiceImpl implements HolidayFinderService {

    @Override
    public boolean isDateAHoliday(LocalDate date, Locale locale) {
        return false;
    }
}
