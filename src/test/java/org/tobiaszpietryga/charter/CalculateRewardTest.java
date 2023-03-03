package org.tobiaszpietryga.charter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CalculateRewardTest extends IntegrationBaseTest {
    private static final long FIRST_CLIENT_ID = 1L;

    @Autowired ObjectMapper objectMapper;

    @Test
    public void shouldReturnTransactionsForClientOne()
            throws Exception {

        mvc.perform(get("/rewards/1?date=2022-01-14")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstMonthPoints", equalTo(0)))
                .andExpect(jsonPath("$.secondMonthPoints", equalTo(2395)))
                .andExpect(jsonPath("$.thirdMonthPoints", equalTo(5)))
                .andExpect(jsonPath("$.totalPoints", equalTo(2400)));
    }

    @Test
    public void shouldReturnMessageWhenClientNotExists()
            throws Exception {

        mvc.perform(get("/rewards/55?date=2022-01-14")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .string("Client not found 55"));
    }
}
