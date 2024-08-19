package org.netz00.simple_banking_system.model.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.dto.TransactionPostResponseDTO;
import org.netz00.simple_banking_system.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionPostResponseMapper extends EntityMapper<TransactionPostResponseDTO, Transaction> {
}
