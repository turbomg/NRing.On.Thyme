package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Integer lastMileage;

  //  @Temporal(TemporalType.TIMESTAMP)
    private Date firstBooking;

  //  @Temporal(TemporalType.TIMESTAMP)
    private Date lastBooking;

    private MileageType mileageType;

    private CarFleet carFleet;

    @Temporal(TemporalType.DATE)
    private Date nextTUV;

    private Integer nextServiceAtKm;

    @OneToOne
    private LocationDefinition currentLocation;

    private CarStatus carStatus;
    private boolean currentlyInUse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarNote> carNotes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<CarNote> carHistory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car")
    private List<PriceListCar> carPricing;

    @OneToOne
    private BookingPackageItemCar bookingPackageItemCar;

    @OneToOne
    private TemporaryPackageItem temporaryPackageItem;

    private boolean isUnderEditing;

    private String carVin;
    private Date carFirstRegistrationDate;
    private BigDecimal carValueFromAdac;

    // Overriding some Lombok defaults

    public List<CarNote> getCarNotes() {
        if (carNotes == null) {
            carNotes = new ArrayList<>();
        }
        return carNotes;
    }

    public void setCarNotes(List<CarNote> carNotes) {
        this.carNotes = carNotes;
    }


    public void addCarNote(CarNote carNote) {
        if (carNote != null) {
            carNote.setCar(this);
            this.carNotes.add(carNote);
        }
    }

    public void removeCarNote(CarNote carNote) {
        if (carNotes != null && this.carNotes.contains(carNote)) {
            this.carNotes.remove(carNote);
            carNote.setCar(null);
        }
    }

}
