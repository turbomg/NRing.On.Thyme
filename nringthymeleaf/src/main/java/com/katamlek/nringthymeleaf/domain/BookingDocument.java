package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class BookingDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar date;

    private String bookingDocumentDescription;

    @Lob
    private String bookingDocumentAdditionalInfo;

    @ManyToOne
    Booking booking;
}
