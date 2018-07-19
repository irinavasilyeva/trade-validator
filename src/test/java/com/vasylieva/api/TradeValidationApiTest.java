package com.vasylieva.api;

import com.vasylieva.model.Error;
import com.vasylieva.model.Spot;
import com.vasylieva.model.ValidationResult;
import com.vasylieva.model.type.TradeType;
import com.vasylieva.service.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TradeValidatorApi.class, secure = false)
public class TradeValidationApiTest {

    @Autowired
    private TradeValidatorApi api;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ValidationService validationService;

    @Test
    public void initiateValidation() throws Exception {
        Spot trade = new Spot();
        trade.setType(TradeType.Spot);
        trade.setTradeDate(LocalDate.of(2018, 7, 19));
        when(validationService.validate(eq(Collections.singletonList(trade)))).thenReturn(
                Collections.singletonList(new ValidationResult(trade, Collections.singleton(new Error("ERROR"))))
        );

        mvc.perform(post("/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getTestData()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].trade.tradeDate", is("2018-07-19")));
    }

    private String getTestData() {
        return "[" +
                "   {" +
                "      \"type\":\"Spot\"," +
                "      \"tradeDate\":\"2018-07-19\"" +
                "   }" +
                "]";
    }
}
