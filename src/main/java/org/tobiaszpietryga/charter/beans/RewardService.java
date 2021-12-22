package org.tobiaszpietryga.charter.beans;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.tobiaszpietryga.charter.dto.RewardDto;
import org.tobiaszpietryga.charter.model.Client;
import org.tobiaszpietryga.charter.model.Transaction;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RewardService {

    private TransactionRepository transactionRepository;

    public RewardDto calculate(Long clientId, LocalDate thresholdDate) {
        List<Transaction> transactions = transactionRepository.findByClientId(clientId);
        RewardDto dto = new RewardDto(-1, -1, -1, -1);
        dto.setFirstMonthPoints(calculatePerPeriod(transactions, thresholdDate, thresholdDate.minusMonths(1)));
        dto.setSecondMonthPoints(calculatePerPeriod(transactions, thresholdDate.minusMonths(1), thresholdDate.minusMonths(2)));
        dto.setThirdMonthPoints(calculatePerPeriod(transactions, thresholdDate.minusMonths(2), thresholdDate.minusMonths(3)));
        dto.setTotalPoints(dto.getFirstMonthPoints() + dto.getSecondMonthPoints() + dto.getThirdMonthPoints());
        return dto;
    }

    private int calculatePerPeriod(List<Transaction> transactions, LocalDate before, LocalDate after) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().toLocalDate().isAfter(after) &&
                        transaction.getDate().toLocalDate().isBefore(before))
                .map(Transaction::getAmount)
                .map(this::calculatePoints)
                .reduce(0, Integer::sum);
    }

    private int calculatePoints(Long amount) {
        int points = 0;
        if (amount > 50) {
            points += amount - 50;
        }
        if (amount > 100) {
            points += 2 * (amount - 100);
        }
        return points;
    }
}
