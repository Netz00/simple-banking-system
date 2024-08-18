package org.netz00.simple_banking_system.model.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.dto.CustomerDTO;
import org.netz00.simple_banking_system.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
}
