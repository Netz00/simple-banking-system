package org.netz00.simple_banking_system.util.csv;

import com.github.javafaker.Faker;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.netz00.simple_banking_system.model.enums.AccountType;
import org.netz00.simple_banking_system.util.csv.entity.AccountCSV;
import org.netz00.simple_banking_system.util.csv.entity.CustomerCSV;
import org.netz00.simple_banking_system.util.csv.entity.TransactionCSV;

import java.io.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Standalone fake (but valid) data generator.
 * TODO: The results should be in gitignore, due it's size, and generation process is deterministic (just use same RANDOM_SEED), except date generation
 * Custom POJOs are used instead of the entity classes to avoid any possibility of changing existing entity structure to comply some fake data generator.
 */

public class DataGenerator {

    private static final int NUM_CUSTOMER_RECORDS = 800;
    private static final int NUM_ACCOUNT_RECORDS = 1000;
    private static final int NUM_TRANSACTION_RECORDS = 100000;
    private static final int RANDOM_SEED = 1243562;
    private static final String OUTPUT_DIR = "src/main/resources/static/";
    private static final LocalDate TRANSACTION_DATE_FROM = LocalDate.of(2023, 1, 1);
    private static final LocalDate TRANSACTION_DATE_TO = LocalDate.of(2024, 1, 1);


    public static void main(String[] args) throws Exception {

        Random random = new Random(RANDOM_SEED);
        Faker faker = new Faker(random);


        // Generate sample customers
        List<CustomerCSV> customers = generateCustomers(NUM_CUSTOMER_RECORDS, faker);
        writeCustomersToCsv(customers, OUTPUT_DIR + "customers.csv");

        // Generate sample accounts
        List<AccountCSV> accounts = generateAccounts(customers, NUM_ACCOUNT_RECORDS, random);

        // Generate sample transactions and store them (avoid using huge collections)
        generateTransactions(accounts, NUM_TRANSACTION_RECORDS, random, faker, OUTPUT_DIR + "transactions.csv");

        // after transactions save accounts with the NEW BALANCE values
        writeAccountsToCsv(accounts, OUTPUT_DIR + "accounts.csv");

    }


    // Generate Customers
    public static List<CustomerCSV> generateCustomers(int numAccounts, Faker faker) {
        List<CustomerCSV> customers = new ArrayList<>();
        for (int i = 1; i <= numAccounts; i++) {
            CustomerCSV customer = CustomerCSV.builder().id((long) i).name(faker.name().fullName()).address(faker.address().streetAddress()).email(faker.internet().emailAddress()).phoneNumber(faker.phoneNumber().phoneNumber()).build();

            customers.add(customer);
        }
        return customers;
    }

    // Generate Accounts
    public static List<AccountCSV> generateAccounts(List<CustomerCSV> customers, int numAccounts, Random random) {

        List<AccountCSV> accounts = new ArrayList<>();

        int customersListSize = customers.size();

        for (int i = 1; i <= numAccounts; i++) {

            // Generate a Gaussian (normal) distributed number
            double gaussian = random.nextGaussian();

            // Map the Gaussian value to a valid list index
            // Assuming mean = listSize / 2, and scale it to the list size range
            double scaledGaussian = (gaussian * (customersListSize / 6.0)) + (customersListSize / 2.0); // Scale for a more even distribution

            accounts.add(AccountCSV.builder().id((long) i).number(10000000L + random.nextInt(90000000)) // Random account number
                    .type(AccountType.values()[random.nextInt(AccountType.values().length)]) // Random account type
                    .balance(Math.round((500.0 + random.nextDouble() * 10000) * 100.0) / 100.0) // Random balance between 500 and 10000
                    .pastMonthTurnover(0d) // Random turnover
                    .customerId((long) Math.clamp((int) Math.round(scaledGaussian), 0, customersListSize - 1)).build());
        }

        return accounts;
    }

    // Generate and store Transactions
    public static void generateTransactions(List<AccountCSV> accounts, int maxTransactions, Random random, Faker faker, String filePath) {

        Date dateFrom = Date.from(TRANSACTION_DATE_FROM.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateTo = Date.from(TRANSACTION_DATE_TO.atStartOfDay(ZoneId.systemDefault()).toInstant());


        TreeSet<Date> dates = new TreeSet<>();
        while (dates.size() <= maxTransactions) {
            dates.add(new Date(ThreadLocalRandom.current().nextLong(dateFrom.getTime(), dateTo.getTime())));
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<TransactionCSV> beanToCsv = new StatefulBeanToCsvBuilder<TransactionCSV>(writer).withSeparator(',').build();

            long transactionId = 1;
            while (transactionId <= maxTransactions) {

                AccountCSV sender = accounts.get(random.nextInt(accounts.size()));
                AccountCSV receiver = accounts.get(random.nextInt(accounts.size()));

                if (receiver.equals(sender) || sender.getBalance() < 500) continue;

                double amount = Math.round((random.nextDouble() * Math.min(sender.getBalance(), 1000)) * 100.0) / 100.0;

                // Write transaction
                beanToCsv.write(TransactionCSV.builder().id(transactionId++).amount(amount).message(faker.food().dish()).dateCreated(dates.pollFirst()).sender_id(sender.getId()).receiver_id(receiver.getId()).build());

                // Update balances transaction after transactions
                sender.setBalance(Math.round((sender.getBalance() - amount) * 100.0) / 100.0);
                receiver.setBalance(Math.round((receiver.getBalance() + amount) * 100.0) / 100.0);
            }
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    // Write Customer data to CSV using OpenCSV
    public static void writeCustomersToCsv(List<CustomerCSV> customers, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<CustomerCSV> beanToCsv = new StatefulBeanToCsvBuilder<CustomerCSV>(writer).withSeparator(',').build();
            beanToCsv.write(customers);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    // Write Account data to CSV using OpenCSV
    public static void writeAccountsToCsv(List<AccountCSV> accounts, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<AccountCSV> beanToCsv = new StatefulBeanToCsvBuilder<AccountCSV>(writer).withSeparator(',').build();
            beanToCsv.write(accounts);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

}
