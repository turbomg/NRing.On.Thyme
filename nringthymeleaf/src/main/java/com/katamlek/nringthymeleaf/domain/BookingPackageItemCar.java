package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class BookingPackageItemCar extends BookingPackageItem {

    private BigDecimal standardDiscount; // cars only, from price list

    @OneToOne
    private PriceListCar priceListCar;

    @OneToOne
    private PricingGroup pricingGroup; // For cars only

    // Detailed Booking car data
    private String model;
    private String plate;

    private CarStatus carStatus;
    private boolean currentlyInUse;

    private BigDecimal kmOut;
    private BigDecimal kmIn;

    @Lob
    private String mechanicNotes;

    @Lob
    private String remarks;

    @OneToOne
    private Car car;

}
