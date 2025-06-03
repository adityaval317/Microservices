package com.aditya.microservices.loans.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "accounts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity {

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    /*@ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    */

    @Column(name="customer_id", nullable = false)
    private int customerId;

    @Column(name="account_number", nullable = false)
    private int accountNumber;

    @Column(name="account_type", nullable = false)
    private String accountType;

    @Column(name="branch_address", nullable = false)
    private String branchAddress;
}
