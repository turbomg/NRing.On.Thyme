package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingNote> bookingNotes;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createDate;

    @NotNull
    private SignatureStatus signatureStatus;

    @NotNull
    private PaymentStatus paymentStatus;

    private boolean emailConfirmationSent;

    @Temporal(TemporalType.DATE)
    private Date emailReminderSendDate;

    private boolean emailReminderSent;

    @ManyToMany
    @JoinTable(name = "booking_customer", joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @NotNull
    private List<Customer> customers; // drivers

    @OneToMany(mappedBy = "booking")
    private List<BookingCar> bookedCarsList;

//    @ManyToMany
//    @JoinTable(name = "booking_pricelist", joinColumns = @JoinColumn(name = "booking_id"),
//            inverseJoinColumns = @JoinColumn(name = "pricelist_id"))
//    private List<OthersPricing> bookedServicesList; // package entries

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingPackageItem> packageItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<PaymentNote> paymentList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingDocument> bookingDocumentList;

    private BookingStatus bookingStatus; // allows for booking write-off when paid and car OK

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingCar> bookingCars;
}
