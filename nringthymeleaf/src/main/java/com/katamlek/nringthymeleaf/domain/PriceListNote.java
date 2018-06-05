package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class PriceListNote extends Note {
    @ManyToOne
    private PriceList priceList;
}
