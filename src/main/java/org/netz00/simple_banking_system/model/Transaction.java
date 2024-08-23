package org.netz00.simple_banking_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_sequence", sequenceName = "transaction_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Size(min = 1, max = 50, message = "Message must be between 1 and 50 characters")
    @NotEmpty(message = "Message is required. Message cannot be null or empty")
    @Column(name = "message", nullable = false)
    private String message;

    // @Setter(AccessLevel.NONE) Setter is required for the database init (Using database objects)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "date_created", nullable = false)
    private Date dateCreated = new Date();

    // Unidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Account sender;

    // Unidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Account receiver;

}
