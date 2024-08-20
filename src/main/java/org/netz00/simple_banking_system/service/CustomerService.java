package org.netz00.simple_banking_system.service;

import org.netz00.simple_banking_system.dto.CustomerDTO;
import org.netz00.simple_banking_system.dto.TransactionGetResponseDTO;
import org.netz00.simple_banking_system.exception.ResourceNotFoundException;
import org.netz00.simple_banking_system.model.enums.TransactionFilter;
import org.netz00.simple_banking_system.model.mapper.CustomerMapper;
import org.netz00.simple_banking_system.model.mapper.TransactionGetResponseMapper;
import org.netz00.simple_banking_system.repository.CustomerRepository;
import org.netz00.simple_banking_system.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final TransactionGetResponseMapper transactionGetResponseMapper;

    private final TransactionRepository transactionRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, TransactionRepository transactionRepository, TransactionGetResponseMapper transactionGetResponseMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.transactionRepository = transactionRepository;
        this.transactionGetResponseMapper = transactionGetResponseMapper;
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {

        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Customer with ID %d not found.", id))
        ));
    }

    @Transactional(readOnly = true)
    public List<TransactionGetResponseDTO> searchTransactions(
            Long id,
            Double amountFrom,
            Double amountTo,
            String messageContains,
            Date dateAfter,
            Date dateBefore,
            TransactionFilter type
    ) {


        return transactionGetResponseMapper.toDto(
                switch (type) {
                    case TransactionFilter.ALL -> transactionRepository.findByIdAll(
                            id,
                            amountFrom,
                            amountTo,
                            messageContains,
                            dateBefore,
                            dateAfter

                    );
                    case TransactionFilter.RECEIVED -> transactionRepository.findByIdReceived(
                            id,
                            amountFrom,
                            amountTo,
                            messageContains,
                            dateBefore,
                            dateAfter

                    );
                    case TransactionFilter.SENT -> transactionRepository.findByIdSent(
                            id,
                            amountFrom,
                            amountTo,
                            messageContains,
                            dateBefore,
                            dateAfter
                    );
                });
    }
}
