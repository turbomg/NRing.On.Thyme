package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Holds general data for all the items "on stock".
 */

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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
