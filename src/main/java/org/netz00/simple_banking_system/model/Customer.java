package org.netz00.simple_banking_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "email",
                        name = "unique_email"),
                @UniqueConstraint(
                        columnNames = "phoneNumber",
                        name = "unique_phoneNumber")
        })
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @NotEmpty(message = "Name is required. Name cannot be null or empty")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 1, max = 50, message = "Address must be between 1 and 50 characters")
    @NotEmpty(message = "Address is required. Address cannot be null or empty")
    @Column(name = "address", nullable = false)
    private String address;

    @Size(min = 3, max = 50, message = "Email must be between 3 and 50 characters")
    @NotEmpty(message = "Email is required. Email cannot be null or empty")
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 1, max = 50, message = "Phone number must be between 1 and 50 characters")
    @NotEmpty(message = "Phone number is required. Phone number cannot be null or empty")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // Unidirectional
    @JoinColumn // without JOIN table
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

}
