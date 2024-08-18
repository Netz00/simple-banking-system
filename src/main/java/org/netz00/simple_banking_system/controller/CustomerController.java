package org.netz00.simple_banking_system.controller;

import org.netz00.simple_banking_system.controller.endpoints.RestEndpoints;
import org.netz00.simple_banking_system.controller.endpoints.RestEndpointsParameters;
import org.netz00.simple_banking_system.dto.CustomerDTO;
import org.netz00.simple_banking_system.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
