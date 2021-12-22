package org.tobiaszpietryga.charter.beans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.tobiaszpietryga.charter.dto.RewardDto;
import org.tobiaszpietryga.charter.model.Transaction;

class RewardServiceTotalTest extends RewardServiceBaseTest {

    @Test
    public void shouldReturnRewardInformationWhenOneTransactionIsProvidedForEveryMonth() {
        //given
        long clientId = 1l;
        Transaction firstMonthTransaction = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_50_AMOUNT);
        Transaction secondMonthTransaction = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_50_AMOUNT);
        Transaction thirdMonthTransaction = prepareTestTransaction(TEST_CLIENT, THIRD_MONTH_DATE, OVER_100_AMOUNT);
        when(transactionRepository.findByClientId(clientId))
                .thenReturn(Arrays.asList(firstMonthTransaction,
                        secondMonthTransaction,
                        thirdMonthTransaction));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getTotalPoints(),
                equalTo(OVER_50_SCORE + OVER_50_SCORE + OVER_100_SCORE));
        assertThat(actualRewardDto.getFirstMonthPoints(),
                equalTo(OVER_50_SCORE));
        assertThat(actualRewardDto.getSecondMonthPoints(),
                equalTo(OVER_50_SCORE));
        assertThat(actualRewardDto.getThirdMonthPoints(),
                equalTo(OVER_100_SCORE));
    }

    @Test
    public void shouldReturnRewardInformationWhenMultipleTransactionsAreProvidedForEveryMonth() {
        //given
        Transaction firstMonthTransactionOver50 = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_50_AMOUNT);
        Transaction firstMonthTransactionOver100 = prepareTestTransaction(TEST_CLIENT, FIRST_MONTH_DATE, OVER_100_AMOUNT);
        Transaction secondMonthTransactionOver50 = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, OVER_50_AMOUNT);
        Transaction secondMonthTransactionNoScore = prepareTestTransaction(TEST_CLIENT, SECOND_MONTH_DATE, NO_SCORING_AMOUNT);
        Transaction thirdMonthTransactionOver100 = prepareTestTransaction(TEST_CLIENT, THIRD_MONTH_DATE, OVER_100_AMOUNT);
        Transaction thirdMonthTransactionOver50 = prepareTestTransaction(TEST_CLIENT, THIRD_MONTH_DATE, OVER_50_AMOUNT);
        Transaction thirdMonthTransactionOverNoScore = prepareTestTransaction(TEST_CLIENT, THIRD_MONTH_DATE, NO_SCORING_AMOUNT);
        when(transactionRepository.findByClientId(TEST_CLIENT_ID))
                .thenReturn(Arrays.asList(firstMonthTransactionOver50,
                        firstMonthTransactionOver100,
                        secondMonthTransactionOver50,
                        secondMonthTransactionNoScore,
                        thirdMonthTransactionOver100,
                        thirdMonthTransactionOver50,
                        thirdMonthTransactionOverNoScore));

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getTotalPoints(),
                equalTo(OVER_50_SCORE + OVER_100_SCORE + OVER_50_SCORE + OVER_100_SCORE + OVER_50_SCORE));
        assertThat(actualRewardDto.getFirstMonthPoints(),
                equalTo(OVER_50_SCORE + OVER_100_SCORE));
        assertThat(actualRewardDto.getSecondMonthPoints(),
                equalTo(OVER_50_SCORE));
        assertThat(actualRewardDto.getThirdMonthPoints(),
                equalTo(OVER_100_SCORE + OVER_50_SCORE));
    }

    @Test
    public void shouldReturnRewardInformationWhenNoTransactionsAreProvided() {
        //given
        when(transactionRepository.findByClientId(TEST_CLIENT_ID))
                .thenReturn(Arrays.asList());

        //when
        RewardDto actualRewardDto = rewardService.calculate(TEST_CLIENT_ID, THRESHOLD_DATE);

        //then
        assertThat(actualRewardDto.getTotalPoints(),
                equalTo(0));
        assertThat(actualRewardDto.getFirstMonthPoints(),
                equalTo(0));
        assertThat(actualRewardDto.getSecondMonthPoints(),
                equalTo(0));
        assertThat(actualRewardDto.getThirdMonthPoints(),
                equalTo(0));
    }
}