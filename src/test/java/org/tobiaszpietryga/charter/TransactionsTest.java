package org.tobiaszpietryga.charter;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@DirtiesContext
public class TransactionsTest extends IntegrationBaseTest {
    private static final long FIRST_CLIENT_ID = 1L;

    @Autowired ObjectMapper objectMapper;

    @Test
    public void shouldReturnTransactions()
            throws Exception {

        Long clientId = FIRST_CLIENT_ID;

        MvcResult mvcResult = mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].amount", equalTo(55)))
                .andExpect(jsonPath("$[0].clientId", equalTo(clientId.intValue())))
                .andReturn();
    }
}
