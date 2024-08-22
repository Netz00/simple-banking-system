package org.netz00.simple_banking_system.util.csv.entity;


import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class TransactionCSV {

    @CsvBindByName
    private Long id;

    @CsvBindByName
    private Double amount;

    @CsvBindByName
    private String message;

    @CsvBindByName
    private Date dateCreated;

    @CsvBindByName
    private Long sender_id;

    @CsvBindByName
    private Long receiver_id;

}
