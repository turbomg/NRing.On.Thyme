package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class PriceList {
    @Id
    @GeneratedValue
    private Long id;

    private String pricingDescription;
    private Long pricingUnit;
    private List<PriceNote> pricingDefinitionNoteList;
    private Long defaultPrice;
    private PriceGroup pricingGroup;


}
