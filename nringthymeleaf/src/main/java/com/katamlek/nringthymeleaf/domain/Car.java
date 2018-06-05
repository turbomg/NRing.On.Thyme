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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "bookedCarsList")
    private List<Booking> bookings;

    @OneToOne
    private BookingCarChangeNote bookingCarChangeNote;
}
