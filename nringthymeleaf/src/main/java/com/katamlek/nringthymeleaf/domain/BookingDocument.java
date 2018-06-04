package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class BookingDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar date;

    private String bookingDocumentDescription;
    private String bookingDocumentAdditionalInfo;
}
