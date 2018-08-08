package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Data
//todo mappedSuperclass - like in Note
public class PriceListCar extends PriceList {
    private BigDecimal startPrice;
    private Integer startKM;
    private Integer per10KM;
    private boolean instruction;

    @OneToOne
    private BookingPackageItemCar bookingPackageItemCar;

    @OneToOne
    private PricingGroup pricingGroup;

    @ManyToOne
    private Car car;

}
