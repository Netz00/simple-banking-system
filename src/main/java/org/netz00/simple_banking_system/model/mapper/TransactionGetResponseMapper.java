package org.netz00.simple_banking_system.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.netz00.simple_banking_system.dto.TransactionGetResponseDTO;
import org.netz00.simple_banking_system.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionGetResponseMapper extends EntityMapper<TransactionGetResponseDTO, Transaction> {
    @Override
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "receiver.id", target = "receiverId")
    TransactionGetResponseDTO toDto(Transaction entity);

}
