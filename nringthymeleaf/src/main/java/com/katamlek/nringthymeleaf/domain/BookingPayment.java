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

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date paymentDate;

//    private OfConcern concerns;

    @OneToOne
    private PaymentNote paymentNote;

    @NotNull
    private BigDecimal paymentAmount;

    // Stores method and account together
    @OneToOne
    private PaymentDefinition paymentDefinition;

    @ManyToOne
    private BookingPackageItem bookingPackageItem;

}
