package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class BookingCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String plate;

    private CarStatus carStatus;
    private boolean currentlyInUse;

    private BigDecimal kmOut;
    private BigDecimal kmIn;

    @Lob
    private String mechanicNotes;

    @Lob
    private String remarks;

    @ManyToOne
    private Booking booking;

}
