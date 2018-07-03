package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class CustomerDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar documentedEnteredOn;

    @ManyToOne
    private User user; // document entered by user xyz

    private String documentType;

    @ManyToOne
    private Customer customer;
}
