package org.netz00.simple_banking_system.util.faker.entity;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCSV {

    @CsvBindByName
    private Long id;

    @CsvBindByName
    private Double amount;

    @CsvBindByName
    private String message;

    @CsvBindByName
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    private Date dateCreated;

    @CsvBindByName
    private Long sender_id;

    @CsvBindByName
    private Long receiver_id;

}
