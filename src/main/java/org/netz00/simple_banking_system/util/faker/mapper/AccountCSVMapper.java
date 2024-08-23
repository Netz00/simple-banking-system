package org.netz00.simple_banking_system.util.faker.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.model.Account;
import org.netz00.simple_banking_system.model.mapper.EntityMapper;
import org.netz00.simple_banking_system.util.faker.entity.AccountCSV;

@Mapper(componentModel = "spring")
public interface AccountCSVMapper extends EntityMapper<AccountCSV, Account> {
}
