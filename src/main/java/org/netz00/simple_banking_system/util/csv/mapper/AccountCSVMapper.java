package org.netz00.simple_banking_system.util.csv.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.model.Account;
import org.netz00.simple_banking_system.model.Customer;
import org.netz00.simple_banking_system.model.mapper.EntityMapper;
import org.netz00.simple_banking_system.util.csv.entity.AccountCSV;
import org.netz00.simple_banking_system.util.csv.entity.CustomerCSV;

@Mapper(componentModel = "spring")
public interface AccountCSVMapper extends EntityMapper<AccountCSV, Account> {
}
