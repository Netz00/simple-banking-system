package org.netz00.simple_banking_system.model.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.dto.AccountDTO;
import org.netz00.simple_banking_system.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDTO, Account> {
}
