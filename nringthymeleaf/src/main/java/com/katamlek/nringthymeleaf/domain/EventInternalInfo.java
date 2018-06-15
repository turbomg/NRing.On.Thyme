package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class EventInternalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String internalEventOrganizer;
    private int internalCurrentEntriesNo;
    private BigDecimal internalCurrentEntriesValue;
    private BigDecimal internalEventCostToRSR;
    private Integer internalSpacesBooked;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventInternalInfo")
    private List<EventInternalInfoNote> eventInternalInfoNote;
}
