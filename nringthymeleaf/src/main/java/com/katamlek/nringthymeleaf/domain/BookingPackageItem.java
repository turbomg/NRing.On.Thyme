package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @OneToOne
    private LocationDefinition locationDefinition;

    private Date date;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    private String description; // car/event/whatever hire type, place, name, a string built from other fields

    private BigDecimal extraDiscountPercent; // concerns all
    private BigDecimal extraDiscountAmount; // concerns all

    private String unit;

    private BigDecimal unitPrice;
    private BigDecimal quantity;

    private BigDecimal total;

    private boolean cancelled;

    private boolean isUnderEditing;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "packageItem")
    private List<BookingPackageItemNote> bookingPackageItemNoteList;
}
