package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Data

public class PriceListCar extends PriceList {
    private BigDecimal startPrice;
    private Integer startKM;
    private Integer per10KM;
    private boolean instruction;
    private String defaultNote;

    @OneToOne
    private BookingPackageItemCar bookingPackageItemCar; //todo I don't think so, item writes it's data to item

    @OneToOne
    private PricingGroup pricingGroup; // todo as above

    @ManyToOne
    private Car car;

    private boolean isValid; // determines if the price list item is "in force"

}
