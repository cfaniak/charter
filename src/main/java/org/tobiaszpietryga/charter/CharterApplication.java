package org.tobiaszpietryga.charter;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tobiaszpietryga.charter.beans.ClientRepository;
import org.tobiaszpietryga.charter.beans.TransactionsController;
import org.tobiaszpietryga.charter.dto.TransactionDto;
import org.tobiaszpietryga.charter.model.Client;

@SpringBootApplication
public class CharterApplication {

//    @Autowired
//    private ClientRepository clientRepository;
//    @Autowired
//    private TransactionsController transactionsController;

    public static void main(String[] args) {
        SpringApplication.run(CharterApplication.class, args);
    }

//    @PostConstruct
//    private void importData() throws URISyntaxException {
//        clientRepository.save(new Client(1l, "John", "john@rambo"));
//        transactionsController
//                .createTransaction(new TransactionDto(60l, Date.valueOf(LocalDate.parse("2021-10-23")), 1l));
//    }
}
