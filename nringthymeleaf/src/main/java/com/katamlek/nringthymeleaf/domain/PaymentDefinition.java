package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class PaymentDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String paymentName;
    private AccountDefinition accountDefinition;
}
