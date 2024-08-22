package org.netz00.simple_banking_system.service;

import org.netz00.simple_banking_system.model.Account;
import org.netz00.simple_banking_system.model.Transaction;
import org.netz00.simple_banking_system.model.enums.TransactionFilter;
import org.netz00.simple_banking_system.repository.AccountRepository;
import org.netz00.simple_banking_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;

@Component
public class ScheduledJob {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ScheduledJob(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * TODO: Pagination, maybe custom queries which return the aggregated sum, definetly not saving account again if there were no transactions...
     */
    @Scheduled(cron = "${cron-expression.past-month-turnover-updater}")
    public void PastMonthTurnoverUpdater() {

        Date firstOfCurrentMonth = Date.from(LocalDate.now().withDayOfMonth(1).plusMonths(4).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date firstOfPreviousMonth = Date.from(LocalDate.now().withDayOfMonth(1).minusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        System.out.println("Monthly turnover update started");
        System.out.println("1st of the current month: " + firstOfCurrentMonth);
        System.out.println("1st of the previous month: " + firstOfPreviousMonth);

        List<Account> accountList = accountRepository.findAll();

        for (Account account : accountList) {

            Double sentAmount = transactionRepository.findByIdSent(
                            account.getId(),
                            null,
                            null,
                            null,
                            firstOfPreviousMonth,
                            firstOfCurrentMonth
                    ).stream().mapToDouble(Transaction::getAmount)
                    .sum();

            Double receivedAmount = transactionRepository.findByIdReceived(
                            account.getId(),
                            null,
                            null,
                            null,
                            firstOfPreviousMonth,
                            firstOfCurrentMonth
                    ).stream().mapToDouble(Transaction::getAmount)
                    .sum();

            account.setPastMonthTurnover(receivedAmount - sentAmount);
            accountRepository.save(account);

        }

        System.out.println("Monthly turnover update finished");

    }
}