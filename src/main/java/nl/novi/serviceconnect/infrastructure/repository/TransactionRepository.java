package nl.novi.serviceconnect.infrastructure.repository;

import nl.novi.serviceconnect.infrastructure.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
