package org.netz00.simple_banking_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "name must not be empty")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "address must not be empty")
    @JsonProperty("address")
    private String address;

    @NotBlank(message = "email must not be empty")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "phoneNumber must not be empty")
    @JsonProperty("phone_number")
    private String phoneNumber;

}