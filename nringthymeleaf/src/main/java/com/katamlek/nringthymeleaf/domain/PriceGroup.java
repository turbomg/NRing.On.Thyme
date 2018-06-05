package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PriceGroup {
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
    private PriceList priceList;
}
