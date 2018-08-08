package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Holds all the items "on stock".
 */

@Entity
@Data
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // General fields
    @Lob
    private String description;
    @Lob
    private String note;
    @OneToOne
    private PricingGroup pricingGroup;

    private PriceListItemValidity priceListItemValidity;

    @OneToOne
    private BookingPackageItem bookingPackageItem;
}
