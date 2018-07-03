package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class CarPricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String description;
    private String defaultNote;
    private BigDecimal startPrice;
    private Integer startKM;
    private Integer per10KM;
    private boolean instruction;

    @OneToOne
    private PricingGroup carPricingGroup;

    @ManyToOne
    private Car car;
}
