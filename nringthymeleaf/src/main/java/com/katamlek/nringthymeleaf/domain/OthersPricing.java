//package com.katamlek.nringthymeleaf.domain;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//
///**
// * Pricing of other services: GoPro, additional driver etc.
// */
//
//@Entity
//@Data
//public class OthersPricing {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Lob
//    private String pricingDescription;
//    private BigDecimal pricingUnit;
//
//    @Lob
//    private String description;
//
//    private BigDecimal defaultPrice;
//
//    @OneToOne
//    private PricingGroup pricingGroup;
//}
