package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class EventInternalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String eventOrganizer;
    private int currentEntriesNo;
    private Long currentEntriesValue;
    private Long eventCostToRSR;
    private int spacesBooked;
    private EventInternalInfoNote eventPrivateNote;
}
