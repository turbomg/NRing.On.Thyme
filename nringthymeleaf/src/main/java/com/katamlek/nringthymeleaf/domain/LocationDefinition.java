package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LocationDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;

    @OneToOne
    private AccountDefinition accountDefinition;

    private boolean hideWhenNoBookings;

    @OneToOne
    private Car car;

    @OneToOne
    private Event event;
}
