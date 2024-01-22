package nl.novi.serviceconnect.repository;

import nl.novi.serviceconnect.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
