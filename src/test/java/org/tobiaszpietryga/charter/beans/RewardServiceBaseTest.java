package org.tobiaszpietryga.charter.beans;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tobiaszpietryga.charter.model.Client;
import org.tobiaszpietryga.charter.model.Transaction;

@SpringBootTest
public abstract class RewardServiceBaseTest {
    protected static final Long TEST_CLIENT_ID = 1L;
    protected static final int OVER_50_AMOUNT = 60;
    protected static final int OVER_50_SCORE = 10;
    protected static final int OVER_100_AMOUNT = 110;
    protected static final int OVER_100_SCORE = OVER_100_AMOUNT -50 + 2*(OVER_100_AMOUNT - 100);
    protected static final int NO_SCORING_AMOUNT = 10;
    protected static final int NO_SCORE = 0;
    protected static final String FIRST_MONTH_DATE = "2021-12-10";
    protected static final String SECOND_MONTH_DATE = "2021-11-10";
    protected static final String THIRD_MONTH_DATE = "2021-10-10";
    protected static final Client TEST_CLIENT = RewardServiceBaseTest.prepareTestClient(TEST_CLIENT_ID);
    protected static LocalDate THRESHOLD_DATE = LocalDate.parse("2021-12-28");
    @Autowired
    protected RewardService rewardService;
    @MockBean
    protected TransactionRepository transactionRepository;

    protected static Client prepareTestClient(long id) {
        Client client = new Client();
        client.setId(id);
        client.setEmail("test@test");
        client.setName("test");
        return client;
    }

    protected Transaction prepareTestTransaction(Client client, String dateText, long amount) {
        LocalDate date = LocalDate.parse(dateText);
        Transaction transaction = new Transaction();
        transaction.setClient(client);
        transaction.setDate(Date.valueOf(date));
        transaction.setAmount(amount);
        return transaction;
    }
}
