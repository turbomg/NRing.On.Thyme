package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CarPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;
    private String defaultNote;
    private Long startPrice;
    private Long startKM;
    private Long per10KM;
    private boolean instruction;
    private String carPricingGroup;

    @ManyToOne
    private Car car;
}
