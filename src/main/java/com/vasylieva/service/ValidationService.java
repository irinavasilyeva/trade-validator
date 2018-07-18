package com.vasylieva.service;

import com.vasylieva.model.Trade;
import com.vasylieva.model.ValidationResult;

import java.util.List;

public interface ValidationService {
    List<ValidationResult> validate(List<Trade> trades);
}
