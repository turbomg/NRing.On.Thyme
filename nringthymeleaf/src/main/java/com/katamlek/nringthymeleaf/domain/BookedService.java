package com.katamlek.nringthymeleaf.domain;

/*
Handles available services, like GoPro, car etc.
 */

//TODO is is some kind of a dictionary data or?

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar bookedFor;

    private Car car;
    private String description;

    private Long unitPrice;
    private int quantity;
    private Long totalAmount;
}
