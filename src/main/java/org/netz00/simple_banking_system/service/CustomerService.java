package org.netz00.simple_banking_system.service;

import org.netz00.simple_banking_system.dto.CustomerDTO;
import org.netz00.simple_banking_system.exception.ResourceNotFoundException;
import org.netz00.simple_banking_system.model.mapper.CustomerMapper;
import org.netz00.simple_banking_system.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public CustomerDTO findById(Long id) {

        return customerMapper.toDto(customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Customer with ID %d not found.", id))
        ));
    }

}
