package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Account definitions used when recording payments.
 */

@Entity
@Data
public class AccountDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String accountName;

    @NotNull
    private String accountCode;

    @OneToOne
    private LocationDefinition locationDefinition;

    @OneToOne
    private PaymentDefinition paymentDefinition;
}
