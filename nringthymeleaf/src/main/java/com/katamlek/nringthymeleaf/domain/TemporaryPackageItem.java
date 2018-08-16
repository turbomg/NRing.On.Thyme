package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class TemporaryPackageItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date itemDate;
    private String startTime; //todo force format - concerns also other classes
    private String itemDescription;
    private BigDecimal itemUnitPrice;
    private Integer itemQuantity;
    private BigDecimal itemTotal;
    private boolean isCancelled;
    private boolean isUnderEditing;

    @ManyToOne(cascade = CascadeType.ALL)
    private Booking booking;

}
