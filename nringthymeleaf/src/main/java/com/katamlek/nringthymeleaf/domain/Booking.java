package com.katamlek.nringthymeleaf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private User createdBy;
    private List<BookingNote> bookingNotes;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createDate;

    private SignatureStatus signatureStatus;
    private PaymentStatus paymentStatus;
    private boolean emailConfirmationSent;

    @Temporal(TemporalType.DATE)
    private Calendar emailReminderSendDate;

    private boolean emailReminderSent;
    private Customer driver;
    private List<BookedService> bookedServiceList; //this one relates to Car via bookedService
    private List<Payment> paymentList;
    private List<BookingDocument> bookingDocumentList;

}
