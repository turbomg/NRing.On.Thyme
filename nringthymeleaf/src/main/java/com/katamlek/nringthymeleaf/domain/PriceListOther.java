package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@Data
public class PriceListOther extends PriceList {
    private BigDecimal pricingUnit;
    private BigDecimal defaultPrice;

    @OneToOne
    private BookingPackageItemOther bookingPackageItemOther;

}
