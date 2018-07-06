package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class OthersPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String pricingDescription;
    private BigDecimal pricingUnit;

    @Lob
    private String description;

    private BigDecimal defaultPrice;

    @OneToOne
    private PricingGroup pricingGroup;
//
//    @ManyToMany(mappedBy = "bookedServicesList")
//    private List<Booking> bookings;
}
