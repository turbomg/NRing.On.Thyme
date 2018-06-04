package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class CustomerEmergencyContact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String customerFirstName;

    private String customerLastName;
    private String customerPhoneNumber;

    @Email
    private String customerEmail;
}
