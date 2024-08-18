package org.netz00.simple_banking_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.netz00.simple_banking_system.model.enums.AccountType;

@Data
public class AccountDTO {

    private Long id;

    @JsonProperty("number")
    private Long number;

    @JsonProperty("type")
    private AccountType type;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("past_month_turnover")
    private Double pastMonthTurnover;
}
