package org.netz00.simple_banking_system.repository;

import org.netz00.simple_banking_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySender_IdOrReceiver_IdOrderByDateCreatedDesc(@NonNull Long id, @NonNull Long id1);
}
