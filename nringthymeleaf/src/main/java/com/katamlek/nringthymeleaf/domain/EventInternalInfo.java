package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class EventInternalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String internalEventOrganizer;
    private int internalCurrentEntriesNo;
    private Long internalCurrentEntriesValue;
    private Long internalEventCostToRSR;
    private int internalSpacesBooked;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventInternalInfo")
    private List<EventInternalInfoNote> eventInternalInfoNote;
}
