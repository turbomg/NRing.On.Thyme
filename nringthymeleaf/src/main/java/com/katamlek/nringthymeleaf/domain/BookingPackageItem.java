package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Items on the booking form derived from pricing lists
 * for Car, Event and Other.
 */

@Entity
@Data
public class BookingPackageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    private String description;
    private BigDecimal unitPrice;
    private BigDecimal quantity;

    @ManyToOne
    private Booking booking;

    private boolean cancelled;
}
