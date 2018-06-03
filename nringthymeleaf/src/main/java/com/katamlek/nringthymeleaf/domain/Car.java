package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private List<Note> carHistory;

    private CarPricing carPricing;

}
