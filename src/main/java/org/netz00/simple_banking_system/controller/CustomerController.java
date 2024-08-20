package org.netz00.simple_banking_system.controller;

import jakarta.validation.constraints.Size;
import org.netz00.simple_banking_system.controller.endpoints.RestEndpoints;
import org.netz00.simple_banking_system.controller.endpoints.RestEndpointsParameters;
import org.netz00.simple_banking_system.dto.CustomerDTO;
import org.netz00.simple_banking_system.dto.TransactionGetResponseDTO;
import org.netz00.simple_banking_system.model.enums.TransactionFilter;
import org.netz00.simple_banking_system.service.CustomerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(RestEndpoints.API_CUSTOMER)
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;

    }

    @GetMapping(RestEndpointsParameters.CUSTOMER_ID)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    // TODO: Recommend implementing Sorting, pagination, more filtering options (by the sender or receiver), sorting by the amount ...
    @GetMapping(RestEndpoints.API_CUSTOMER_HISTORY + RestEndpointsParameters.CUSTOMER_ID)
    public ResponseEntity<List<TransactionGetResponseDTO>> getCustomerTransactions(
            @PathVariable("id") final Long id,
            @RequestParam(name = "min_amount", required = false) Double amountFrom,
            @RequestParam(name = "max_amount", required = false) Double amountTo,
            @RequestParam(name = "message_contains", required = false) @Size(max = 50) String messageContains,
            @RequestParam(name = "date_from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateAfter,
            @RequestParam(name = "date_to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateBefore,
            @RequestParam(name = "type", required = false) TransactionFilter type
    ) {
        if (isNull(type))
            type = TransactionFilter.ALL;

        return new ResponseEntity<>(
                customerService.searchTransactions(
                        id,
                        amountFrom,
                        amountTo,
                        messageContains,
                        dateAfter,
                        dateBefore,
                        type
                ), HttpStatus.OK
        );
    }


}
