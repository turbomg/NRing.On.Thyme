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

    private Date startDateTime;
    private Date endDateTime;

    @OneToOne
    private Event event;

    @OneToOne
    private LocationDefinition locationDefinition;

    @OneToOne
    private Car car;

    @OneToOne
    private User instructor;

    @Lob
    private String mechanicRemarks;

    private String itemDescription;

    private BigDecimal itemUnitPrice;
    private String unitOfMeasure;
    private Integer itemQuantity;
    private BigDecimal itemTotal;
    private boolean isCancelled;
    private boolean isUnderEditing;

    @ManyToOne(cascade = CascadeType.ALL)
    private Booking booking;

    private Integer statistiscCount; // how many times count the item for calendar and other stats

}
