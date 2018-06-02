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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar createDate; // = Customer since
    private User createdBy;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String email;
    private boolean newsletter;
    private Agent agent;
    private CustomerGroup customerGroup;
    private List<Entry> customerNotes;
    private CustomerEmergencyContact customerEmergencyContact;
    private List<CustomerDocument> customerDocuments;
    private List<Entry> customerHistory;

    @Temporal(TemporalType.DATE)
    Calendar lastEdit;
}
