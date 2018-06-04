package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class PaymentNote extends Note {
    private String paymentMethod;

    @NotNull
    private Long paymentAmount;

}
