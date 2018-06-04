package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class EventInternalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String internalEventOrganizer;
    private int internalCurrentEntriesNo;
    private Long internalCurrentEntriesValue;
    private Long internalEventCostToRSR;
    private int internalSpacesBooked;
    private List<EventInternalInfoNote> eventInternalInfoNote;
}
