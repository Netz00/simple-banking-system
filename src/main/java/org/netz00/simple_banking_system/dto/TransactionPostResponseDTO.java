package org.netz00.simple_banking_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionPostResponseDTO {

    @JsonProperty("id")
    private Long id;

}
