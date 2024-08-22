package org.netz00.simple_banking_system.service;


import org.netz00.simple_banking_system.dto.TransactionPostResponseDTO;
import org.netz00.simple_banking_system.dto.TransactionRequestDTO;
import org.netz00.simple_banking_system.exception.ResourceNotFoundException;
import org.netz00.simple_banking_system.model.Account;
import org.netz00.simple_banking_system.model.Transaction;
import org.netz00.simple_banking_system.model.mapper.TransactionPostResponseMapper;
import org.netz00.simple_banking_system.repository.AccountRepository;
import org.netz00.simple_banking_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionPostResponseMapper transactionPostResponseMapper;

    private final AccountRepository accountRepository;


    public TransactionService(TransactionRepository transactionRepository, TransactionPostResponseMapper transactionPostResponseMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionPostResponseMapper = transactionPostResponseMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional()
    public TransactionPostResponseDTO save(TransactionRequestDTO transactionRequestDTO) {

        // TODO: User authorized?

        // Accounts exist?
        // TODO: Find sender and receiver in the single query

        Account sender = accountRepository.findById(transactionRequestDTO.getSenderId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Account with ID %d not found.", (transactionRequestDTO.getSenderId()))
                ));

        Account receiver = accountRepository.findById(transactionRequestDTO.getReceiverId()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Account with ID %d not found.", (transactionRequestDTO.getReceiverId()))
                ));

        // Sender has sufficient balance?

        if (sender.getBalance() < transactionRequestDTO.getAmount())
            throw new ResourceNotFoundException("Insufficient amount to complete transaction.");    // TODO: define a decent exception

        // Perform transaction.

        sender.setBalance(sender.getBalance() - transactionRequestDTO.getAmount());
        receiver.setBalance(receiver.getBalance() + transactionRequestDTO.getAmount());

        // Save transaction.

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setMessage(transactionRequestDTO.getMessage());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        return transactionPostResponseMapper.toDto(
                transactionRepository.save(transaction)
        );
    }


}
