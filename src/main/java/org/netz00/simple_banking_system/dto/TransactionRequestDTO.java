package org.netz00.simple_banking_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequestDTO {

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("message")
    String message;

    @JsonProperty("sender_id")
    Long senderId;

    @JsonProperty("receiver_id")
    Long receiverId;

}
