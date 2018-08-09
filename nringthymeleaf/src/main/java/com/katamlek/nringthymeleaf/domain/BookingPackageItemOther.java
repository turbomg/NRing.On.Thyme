package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BookingPackageItemOther extends BookingPackageItem {

    @OneToOne
    private PriceListOther priceListOther;

}
