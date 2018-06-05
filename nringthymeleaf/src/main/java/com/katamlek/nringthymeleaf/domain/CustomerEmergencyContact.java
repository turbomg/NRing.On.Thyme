package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class CustomerEmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String customerFirstName;

    private String customerLastName;
    private String customerPhoneNumber;

    @Email
    private String customerEmail;

    @ManyToMany(mappedBy = "customerEmergencyContacts")
    private List<Customer> customers;
}
