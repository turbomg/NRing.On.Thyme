package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BookingPackageItemEvent extends BookingPackageItem {

    @OneToOne
    private Event event;

    @OneToOne
    private PriceListEvent priceListEvent;

    @ManyToOne
    private Booking booking;

}
