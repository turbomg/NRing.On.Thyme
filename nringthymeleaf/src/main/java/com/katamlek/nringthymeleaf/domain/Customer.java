package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar customerCreateDate; // = Customer since

    @ManyToOne
    private User customerCreatedBy;

    @NotNull
    private String customerFirstName;

    private String customerLastName;
    private String customerAddress;
    private String customerPhoneNumber;

    @Email
    private String customerEmail;

    private boolean customerNewsletter;

    @Lob
    private String customerGeneralInformation;

    @OneToOne
    private Agent customerAgent;

    private CustomerGroup customerGroup;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerNote> customerNotes;

    @ManyToMany
    @JoinTable(name = "customer_customeremergencycontact", joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "customeremergencycontact_id"))
    private List<CustomerEmergencyContact> customerEmergencyContacts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerDocument> customerDocuments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<CustomerNote> customerHistory;

    @Temporal(TemporalType.DATE)
    Calendar customerLastEdit;

    @ManyToMany(mappedBy = "customers")
    private List<Booking> bookings;
}
