package org.tobiaszpietryga.charter.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tobiaszpietryga.charter.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}