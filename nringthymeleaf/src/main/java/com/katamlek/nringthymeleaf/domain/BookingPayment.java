package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class BookingPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Temporal(TemporalType.DATE)
    @NotNull
    private Date paymentDate;

//    private OfConcern concerns;

    private String paymentRemarks;

    @NotNull
    private BigDecimal paymentAmount;

    // Stores method and account together
    @OneToOne
    private PaymentDefinition paymentDefinition;

    @ManyToOne
    private Booking booking;

    private boolean isUnderEditing;

    public PaymentDefinition getPaymentDefinition() {
        if (paymentDefinition == null) {
            paymentDefinition = new PaymentDefinition();
        }
        return paymentDefinition;
    }

    public void setPaymentDefinition(PaymentDefinition paymentDefinition) {
        this.paymentDefinition = paymentDefinition;
    }
}
