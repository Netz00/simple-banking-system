package org.netz00.simple_banking_system.repository;

import org.netz00.simple_banking_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
            select t from Transaction t
            where (t.sender.id = :id or t.receiver.id = :id)
            and
            (
                (:amountFrom IS NULL or t.amount >= :amountFrom)
                and (:amountTo IS NULL or t.amount <= :amountTo)
                and (:message IS NULL or upper(t.message) like upper(:message))
                and (:dateAfter IS NULL or t.dateCreated >= :dateAfter)
                and (:dateBefore IS NULL or t.dateCreated <= :dateBefore)
            )
            order by t.dateCreated DESC
            """)
    List<Transaction> findByIdAll(
            @Param("id") @NonNull Long id,
            @Param("amountFrom") @Nullable Double amountFrom,
            @Param("amountTo") @Nullable Double amountTo,
            @Param("message") @Nullable String message,
            @Param("dateAfter") @Nullable Date dateAfter,
            @Param("dateBefore") @Nullable Date dateBefore
    );

    @Query("""
            select t from Transaction t
            where (t.sender.id = :id)
            and
            (
                (:amountFrom IS NULL or t.amount >= :amountFrom)
                and (:amountTo IS NULL or t.amount <= :amountTo)
                and (:message IS NULL or upper(t.message) like upper(:message))
                and (:dateAfter IS NULL or t.dateCreated >= :dateAfter)
                and (:dateBefore IS NULL or t.dateCreated <= :dateBefore)
            )
            order by t.dateCreated DESC
            """)
    List<Transaction> findByIdSent(
            @Param("id") @NonNull Long id,
            @Param("amountFrom") @Nullable Double amountFrom,
            @Param("amountTo") @Nullable Double amountTo,
            @Param("message") @Nullable String message,
            @Param("dateAfter") @Nullable Date dateAfter,
            @Param("dateBefore") @Nullable Date dateBefore
    );

    @Query("""
            select t from Transaction t
            where (t.receiver.id = :id)
            and
            (
                (:amountFrom IS NULL or t.amount >= :amountFrom)
                and (:amountTo IS NULL or t.amount <= :amountTo)
                and (:message IS NULL or upper(t.message) like upper(:message))
                and (:dateAfter IS NULL or t.dateCreated >= :dateAfter)
                and (:dateBefore IS NULL or t.dateCreated <= :dateBefore)
            )
            order by t.dateCreated DESC
            """)
    List<Transaction> findByIdReceived(
            @Param("id") @NonNull Long id,
            @Param("amountFrom") @Nullable Double amountFrom,
            @Param("amountTo") @Nullable Double amountTo,
            @Param("message") @Nullable String message,
            @Param("dateAfter") @Nullable Date dateAfter,
            @Param("dateBefore") @Nullable Date dateBefore
    );

}
