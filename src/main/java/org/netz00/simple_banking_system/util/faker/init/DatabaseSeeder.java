package org.netz00.simple_banking_system.util.faker.init;

import org.netz00.simple_banking_system.model.Account;
import org.netz00.simple_banking_system.model.Customer;
import org.netz00.simple_banking_system.model.Transaction;
import org.netz00.simple_banking_system.repository.AccountRepository;
import org.netz00.simple_banking_system.repository.CustomerRepository;
import org.netz00.simple_banking_system.repository.TransactionRepository;
import org.netz00.simple_banking_system.util.faker.CsvParser;
import org.netz00.simple_banking_system.util.faker.entity.AccountCSV;
import org.netz00.simple_banking_system.util.faker.entity.CustomerCSV;
import org.netz00.simple_banking_system.util.faker.entity.TransactionCSV;
import org.netz00.simple_banking_system.util.faker.mapper.AccountCSVMapper;
import org.netz00.simple_banking_system.util.faker.mapper.CustomerCSVMapper;
import org.netz00.simple_banking_system.util.faker.mapper.TransactionCSVMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.*;


/**
 * Following code is written only and only to follow the specification. Usually existing tools such as Liquibase Or "Spring Boot way" handle database initialization.
 * <p>
 * <p>
 * Pipeline:
 * ¸¸¸¸¸¸ * --> open csv file -------------> Load AccountCSV ----------> map to -> Accounts ---> batched insertion in DB ----------
 * ¸¸¸¸¸ /                                                             \                                                           \
 * START --------> open csv file --> Load CustomerCSV ----------------- * ---> map to ---> Customer ---> batched insertion in DB -- * -----> END
 * ¸¸¸¸¸ \                                                              \                                                          /
 * ¸¸¸¸¸¸ * ------> open csv file -> Load TransactionCSV --------------- * --> map to -> Transaction --> batched insertion in DB --
 * <p>
 * CONS: too much memory consumption, for storing all accounts in memory, but transactions and customer are batched and inserted
 * PROS: we got all entities loaded, so we don't need to query database, e.g. to find account with specific ID before assigning it to transaction
 */

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final String INPUT_DIR = "src/main/resources/static/";
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private static final int PARALLELISM = Runtime.getRuntime().availableProcessors();
    private static final CsvParser<CustomerCSV> customerCsvParser = new CsvParser<>();
    private static final CsvParser<AccountCSV> accountCsvParser = new CsvParser<>();
    private static final CsvParser<TransactionCSV> transactionCsvParser = new CsvParser<>();

    private final CustomerCSVMapper customerCSVMapper;
    private final AccountCSVMapper accountCSVMapper;
    private final TransactionCSVMapper transactionCSVMapper;

    public DatabaseSeeder(CustomerRepository customerRepository,
                          TransactionRepository transactionRepository,
                          AccountRepository accountRepository,
                          CustomerCSVMapper customerCSVMapper,
                          AccountCSVMapper accountCSVMapper,
                          TransactionCSVMapper transactionCSVMapper
    ) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.customerCSVMapper = customerCSVMapper;
        this.accountCSVMapper = accountCSVMapper;
        this.transactionCSVMapper = transactionCSVMapper;
    }

    /**
     * 16.2 s (14.7 s Database) without multithreading
     * TODO: Focus on the Hibernate insertion time
     */
    @Override
    public void run(String... args) throws IOException {

        System.out.println("Staring Database initialization...");
        long startTime = System.currentTimeMillis();


        List<CustomerCSV> customerCSVs = customerCsvParser.parseCSV(CustomerCSV.class, INPUT_DIR + "customers.csv");
        List<AccountCSV> accountCSVs = accountCsvParser.parseCSV(AccountCSV.class, INPUT_DIR + "accounts.csv");
        List<TransactionCSV> transactionCSVs = transactionCsvParser.parseCSV(TransactionCSV.class, INPUT_DIR + "transactions.csv");


        Map<Long, Customer> customerMap = new HashMap<>();
        for (CustomerCSV customerCSV : customerCSVs) {
            customerMap.put(customerCSV.getId(), customerCSVMapper.toEntity(customerCSV));
        }

        Map<Long, Account> accountsMap = new HashMap<>();
        for (AccountCSV accountCSV : accountCSVs) {
            Account account = accountCSVMapper.toEntity(accountCSV);
            accountsMap.put(accountCSV.getId(), account);
            customerMap.get(accountCSV.getCustomerId()).getAccounts().add(account);
        }

        List<Transaction> transactions = new LinkedList<>();
        for (TransactionCSV transactionCSV : transactionCSVs) {
            Transaction transaction = transactionCSVMapper.toEntity(transactionCSV);
            transaction.setReceiver(accountsMap.get(transactionCSV.getReceiver_id()));
            transaction.setSender(accountsMap.get(transactionCSV.getSender_id()));
            transactions.add(transaction);
        }

        long beforeSaving = System.currentTimeMillis();

        // Good luck persistence :)
        customerRepository.saveAll(customerMap.values());
        accountRepository.saveAll(accountsMap.values());    // TODO: without this only 623 accounts are loaded
        transactionRepository.saveAll(transactions);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        long elapsedTimebeforeSaving = beforeSaving - startTime;
        System.out.println("Database initialized!");
        System.out.println("Elapsed time before saving: " + elapsedTimebeforeSaving + "ms\tCPU: " + PARALLELISM);
        System.out.println("Elapsed time total: " + elapsedTime + "ms\tCPU: " + PARALLELISM);
    }


}

