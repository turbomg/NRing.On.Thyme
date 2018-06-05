package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String pricingDescription;
    private Long pricingUnit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priceList")
    private List<PriceListNote> pricingDefinitionListNote;

    private Long defaultPrice;

    @OneToOne
    private PriceGroup pricingGroup;

    @ManyToMany(mappedBy = "bookedServicesList")
    private List<Booking> bookings;
}
