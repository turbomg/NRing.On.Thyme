package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private User createdBy;

    private List<BookingNote> bookingNotes;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Calendar createDate;

    @NotNull
    private SignatureStatus signatureStatus;

    @NotNull
    private PaymentStatus paymentStatus;

    @Email
    private boolean emailConfirmationSent;

    @Temporal(TemporalType.DATE)
    private Calendar emailReminderSendDate;

    private boolean emailReminderSent;

    @NotNull
    private Customer driver;

    private List<Car> bookedCarsList;

    private List<PriceList> bookedServicesList;

    private List<Payment> paymentList;
    private List<BookingDocument> bookingDocumentList;

    private BookingStatus bookingStatus; // dodatkowo, zeby moc zamykac zlecenia po sprawdzeniu samochodu
}
