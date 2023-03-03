package org.tobiaszpietryga.charter.beans;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.tobiaszpietryga.charter.dto.RewardDto;
import org.tobiaszpietryga.charter.model.Transaction;

class RewardServiceFirstMonthTest extends RewardServiceBaseTest {
    @Test
    public void shouldReturnRewardInformationWhenOneTransactionIsProvidedForFirstMonth() {
        //given
        long clientId = 1l;
        Transaction transaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_50_AMOUNT);
        when(transactionRepository.findByClientId(clientId)).thenReturn(Arrays.asList(transaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getFirstMonthPoints(), CoreMatchers.equalTo(OVER_50_SCORE));
    }

    @Test
    public void shouldReturnRewardInformationWhenAllScoringTypeTransactionsAreProvidedForFirstMonth() {
        //given
        long clientId = 1l;
        Transaction onePointTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_50_AMOUNT);
        Transaction twoPointTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_100_AMOUNT);
        Transaction noPointTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, NO_SCORING_AMOUNT);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(onePointTransaction,
                        twoPointTransaction,
                        noPointTransaction));

        int expectedFirstMonthScore = OVER_50_SCORE + OVER_100_SCORE + NO_SCORE;

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getFirstMonthPoints(), CoreMatchers.equalTo(expectedFirstMonthScore));
    }

    @Test
    public void shouldReturnRewardInformationWhenNoTransactionsAreProvidedForFirstMonth() {
        //given
        long clientId = 1l;
        Transaction secondMonthTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, 1234567);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(secondMonthTransaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, LocalDate.now());

        //then
        assertThat(actualRewardDto.getFirstMonthPoints(), CoreMatchers.equalTo(0));
    }

    @Test
    public void shouldReturnRewardInformationForFirstMonthWhenTransactionsAreProvidedForFirstAndSecondMonth() {
        //given
        long clientId = 1l;
        Transaction secondMonthTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, 1234567);
        Transaction firstMonthTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_100_AMOUNT);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(secondMonthTransaction,
                        firstMonthTransaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, LocalDate.parse("2021-12-22"));

        //then
        assertThat(actualRewardDto.getFirstMonthPoints(), CoreMatchers.equalTo(OVER_100_SCORE));
    }
}