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
    private String customerEmergencyFirstName;

    private String customerEmergencyLastName;
    private String customerEmergencyPhoneNumber;

    @Email
    private String customerEmergcencyEmail;

    @ManyToMany(mappedBy = "customerEmergencyContacts")
    private List<Customer> customers;
}
