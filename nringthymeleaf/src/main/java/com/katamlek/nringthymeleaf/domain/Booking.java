package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingNote> bookingNotes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingCarChangeNote> carChangeNotes;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Calendar createDate;

    @NotNull
    private SignatureStatus signatureStatus;

    @NotNull
    private PaymentStatus paymentStatus;

    private boolean emailConfirmationSent;

    @Temporal(TemporalType.DATE)
    private Calendar emailReminderSendDate;

    private boolean emailReminderSent;

    @ManyToMany
    @JoinTable(name = "booking_customer", joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @NotNull
    private List<Customer> customers; // drivers

    @ManyToMany
    @JoinTable(name = "booking_car", joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<Car> bookedCarsList;

    //TODO as above
    @ManyToMany
    @JoinTable(name = "booking_pricelist", joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "pricelist_id"))
    private List<PriceList> bookedServicesList; // package entries

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<PaymentNote> paymentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingDocument> bookingDocumentList;

    private BookingStatus bookingStatus; // allows for booking write-off when paid and car OK
}
