package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class BookingPackageItemCar extends BookingPackageItem {

    private BigDecimal standardDiscount; // cars only, from price list

    @OneToOne
    private Car car;

    @OneToOne
    private PriceListCar priceListCar;

    @OneToOne
    private PricingGroup pricingGroup; // For cars only

    @ManyToOne
    private Booking booking;

}
