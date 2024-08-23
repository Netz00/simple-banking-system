package org.netz00.simple_banking_system.util.faker.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.model.Customer;
import org.netz00.simple_banking_system.model.mapper.EntityMapper;
import org.netz00.simple_banking_system.util.faker.entity.CustomerCSV;

@Mapper(componentModel = "spring")
public interface CustomerCSVMapper extends EntityMapper<CustomerCSV, Customer> {
}
