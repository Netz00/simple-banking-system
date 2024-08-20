package org.netz00.simple_banking_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionGetResponseDTO {

    private Long id;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("message")
    String message;

    @JsonProperty("date_created")
    Date dateCreated;

    @JsonProperty("sender_id")
    Long senderId;

    @JsonProperty("receiver_id")
    Long receiverId;

}
