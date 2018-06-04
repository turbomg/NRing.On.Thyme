package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String model;
    private String plate;

    private CarColor carColor;
    private Long lastMileage;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar firstBooking;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastBooking;

    private MileageType mileageType;
    private CarFleet carFleet;

    @Temporal(TemporalType.DATE)
    private Calendar nextTUV;

    private Long nextServiceAtKm;

    private LocationDefinition currentLocation;

    private CarStatus carStatus;
    private boolean currentlyInUse;

    private List<CarNote> carNotes;
    private List<CarNote> carHistory;

    private CarPricing carPricing;
}
