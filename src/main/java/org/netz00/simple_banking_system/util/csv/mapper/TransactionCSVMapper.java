package org.netz00.simple_banking_system.util.csv.mapper;

import org.mapstruct.Mapper;
import org.netz00.simple_banking_system.model.Customer;
import org.netz00.simple_banking_system.model.Transaction;
import org.netz00.simple_banking_system.model.mapper.EntityMapper;
import org.netz00.simple_banking_system.util.csv.entity.CustomerCSV;
import org.netz00.simple_banking_system.util.csv.entity.TransactionCSV;

@Mapper(componentModel = "spring")
public interface TransactionCSVMapper extends EntityMapper<TransactionCSV, Transaction> {
}
