package org.netz00.simple_banking_system.util.csv.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class CustomerCSV {

    @CsvBindByName
    private Long id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String address;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String phoneNumber;

}
