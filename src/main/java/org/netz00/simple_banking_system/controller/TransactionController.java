package org.netz00.simple_banking_system.controller;

import jakarta.validation.Valid;
import org.netz00.simple_banking_system.controller.endpoints.RestEndpoints;
import org.netz00.simple_banking_system.dto.TransactionRequestDTO;
import org.netz00.simple_banking_system.dto.TransactionPostResponseDTO;
import org.netz00.simple_banking_system.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestEndpoints.API_TRANSACTION)
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;

    }

    @PostMapping()
    public ResponseEntity<TransactionPostResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO) {
        return new ResponseEntity<>(
                transactionService.save(transactionRequestDTO), HttpStatus.CREATED
        );
    }


}
