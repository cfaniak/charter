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
    public static void main(String[] args) {
        SpringApplication.run(CharterApplication.class, args);
    }
}
