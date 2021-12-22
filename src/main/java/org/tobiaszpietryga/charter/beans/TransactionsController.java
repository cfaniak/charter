package org.tobiaszpietryga.charter.beans;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tobiaszpietryga.charter.dto.TransactionDto;
import org.tobiaszpietryga.charter.model.Client;
import org.tobiaszpietryga.charter.model.Transaction;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionsController {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    @GetMapping
    public List<TransactionDto> getTransactions() {
        return transactionRepository
                .findAll()
                .stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createTransaction(@RequestBody TransactionDto transactionDto)
            throws URISyntaxException {
        Client client = clientRepository.findById(transactionDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found " + transactionDto.getClientId()));
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDto.getAmount());
        newTransaction.setDate(transactionDto.getDate());
        newTransaction.setClient(client);
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        return ResponseEntity.created(new URI("/transactions/" + savedTransaction.getId())).body("not yet implemented");
    }

    @PostConstruct
    private void importData() {

    }
}