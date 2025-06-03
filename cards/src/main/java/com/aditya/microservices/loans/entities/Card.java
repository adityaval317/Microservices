package com.aditya.microservices.loans.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cards")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card extends BaseEntity {

    @Id
    @Column(name="card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cardId;

    @Column(name="mobile_number", nullable = false)
    private String mobileNumber;

    @Column(name="card_number", nullable = false)
    private String cardNumber;

    @Column(name="card_type", nullable = false)
    private String cardType;

    @Column(name="total_limit", nullable = false)
    private double totalLimit;

    @Column(name="amount_used", nullable = false)
    private double amountUsed;

    @Column(name="available_amount", nullable = false)
    private double availableAmount;
}
