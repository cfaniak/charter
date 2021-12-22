package org.tobiaszpietryga.charter.dto;

import java.sql.Date;

import org.tobiaszpietryga.charter.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDto {
    Long amount;
    Date date;
    Long clientId;

    public static TransactionDto from(Transaction transaction) {
        return new TransactionDto(transaction.getAmount(), transaction.getDate(),
                transaction.getClient().getId());
    }
}
