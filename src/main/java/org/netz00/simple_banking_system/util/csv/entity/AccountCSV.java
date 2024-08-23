package org.netz00.simple_banking_system.util.csv.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.netz00.simple_banking_system.model.enums.AccountType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCSV {

    @CsvBindByName
    private Long id;

    @CsvBindByName
    private Long number;

    @CsvBindByName
    private AccountType type;

    @CsvBindByName
    private Double balance;

    @CsvBindByName
    private Double pastMonthTurnover;

    @CsvBindByName
    private Long customerId;
}
