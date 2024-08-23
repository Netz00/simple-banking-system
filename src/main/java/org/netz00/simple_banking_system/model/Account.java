package org.netz00.simple_banking_system.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.netz00.simple_banking_system.model.enums.AccountType;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "account")
public class Account {

    @Id
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "number")
    private Long number;

    @NotNull(message = "Account type is required. Account type cannot be null or empty")
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "past_month_turnover")
    private Double pastMonthTurnover;

}
