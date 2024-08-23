package org.netz00.simple_banking_system.util.faker.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
