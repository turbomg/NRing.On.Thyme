package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class LocationDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String locationName;
    private AccountDefinition accountDefinition;
    private boolean hideWhenNoBookings;
}
