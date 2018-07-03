package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PricingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pricingGroupName;
    private String pricingGroupUnit;
    private Long pricingGroupDefaultDiscount;
    private int pricingGroupStartQuantity;
    private int pricingGroupEndQuantity;
    private Long pricingGroupRounding;

    @OneToOne
    private OthersPricing priceList;

    @OneToOne
    private CarPricing carPricing;
}
