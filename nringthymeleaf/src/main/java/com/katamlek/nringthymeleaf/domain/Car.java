package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * The fleet objects.
 */

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String model;
    private String plate;

    private CarColor carColor;
    private Long lastMileage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date firstBooking;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastBooking;

    private MileageType mileageType;

    private CarFleet carFleet;

    @Temporal(TemporalType.DATE)
    private Date nextTUV;

    private Long nextServiceAtKm;

    @OneToOne
    private LocationDefinition currentLocation;

    private CarStatus carStatus;
    private boolean currentlyInUse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarNote> carNotes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarNote> carHistory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarPricing> carPricing;

}
