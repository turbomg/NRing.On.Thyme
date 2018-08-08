package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Data
//todo map superclass
public class PriceListEvent extends PriceList {
    private BigDecimal eventEntryFee;
    private BigDecimal additionalDriverFee;

    @OneToOne
    private BookingPackageItemEvent bookingPackageItemEvent;

    @OneToOne
    private Event event;

}
