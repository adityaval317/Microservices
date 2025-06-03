package com.aditya.microservices.loans.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "loans")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity {

    @Id
    @Column(name="loan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanId;

    @Column(name="mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name="loan_number", nullable = false)
    private long loanNumber;

    @Column(name="loan_type", nullable = false)
    private String loanType;

    @Column(name="amount_paid", nullable = false)
    private double amountPaid;

    @Column(name="outstanding_amount", nullable = false)
    private double outstandingAmount;
}
