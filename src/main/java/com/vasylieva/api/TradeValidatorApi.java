package com.vasylieva.api;

import com.vasylieva.model.Trade;
import com.vasylieva.model.ValidationResult;
import com.vasylieva.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradeValidatorApi {

    @Autowired
    private ValidationService validationService;

    @RequestMapping(path = "/validate", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValidationResult> validate(@RequestBody List<Trade> trades) {
        return validationService.validate(trades);
    }
}
