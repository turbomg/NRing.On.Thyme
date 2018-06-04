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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar customerCreateDate; // = Customer since

    private User customerCreatedBy;

    @NotNull
    private String customerFirstName;

    private String customerLastName;
    private String customerAddress;
    private String customerPhoneNumber;

    @Email
    private String customerEmail;

    private boolean customerNewsletter;
    private Agent customerAgent;
    private CustomerGroup customerGroup;
    private List<Note> customerNotes;
    private CustomerEmergencyContact customerEmergencyContact;
    private List<CustomerDocument> customerDocuments;
    private List<Note> customerHistory;

    @Temporal(TemporalType.DATE)
    Calendar customerLastEdit;
}
