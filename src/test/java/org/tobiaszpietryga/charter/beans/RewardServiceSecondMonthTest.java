package org.tobiaszpietryga.charter.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.tobiaszpietryga.charter.dto.RewardDto;
import org.tobiaszpietryga.charter.model.Client;
import org.tobiaszpietryga.charter.model.Transaction;

class RewardServiceSecondMonthTest extends RewardServiceBaseTest {

    @Test
    public void shouldReturnRewardInformationWhenOneTransactionIsProvidedForSecondMonth() {
        //given
        long clientId = 1l;
        Transaction transaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_50_AMOUNT);
        when(transactionRepository.findByClientId(clientId)).thenReturn(Arrays.asList(transaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getSecondMonthPoints(), CoreMatchers.equalTo(OVER_50_SCORE));
    }

    @Test
    public void shouldReturnRewardInformationWhenAllScoringTypeTransactionsAreProvidedForSecondMonth() {
        //given
        long clientId = 1l;
        Transaction onePointTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_50_AMOUNT);
        Transaction twoPointTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_100_AMOUNT);
        Transaction noPointTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, NO_SCORING_AMOUNT);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(onePointTransaction,
                        twoPointTransaction,
                        noPointTransaction));

        int expectedSecondMonthScore = OVER_50_SCORE + OVER_100_SCORE + NO_SCORE;

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getSecondMonthPoints(), CoreMatchers.equalTo(expectedSecondMonthScore));
    }

    @Test
    public void shouldReturnRewardInformationWhenNoTransactionsAreProvidedForSecondMonth() {
        //given
        long clientId = 1l;
        Transaction firstMonthTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, 1234567);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(firstMonthTransaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getSecondMonthPoints(), CoreMatchers.equalTo(0));
    }

    @Test
    public void shouldReturnRewardInformationForSecondMonthWhenTransactionsAreProvidedForFirstAndSecondMonth() {
        //given
        long clientId = 1l;
        Transaction secondMonthTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_100_AMOUNT);
        Transaction firstMonthTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, 123456);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(secondMonthTransaction,
                        firstMonthTransaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getSecondMonthPoints(), CoreMatchers.equalTo(OVER_100_SCORE));
    }
}