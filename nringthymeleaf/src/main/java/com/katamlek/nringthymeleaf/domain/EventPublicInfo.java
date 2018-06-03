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
public class EventPublicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String eventCarTypes;
    private String eventTyreTypes;
    private String eventNoiseLimit;
    private String eventTrackdayFormat;
    private String eventPitlaneInUse;
    private EventPublicInfoNote eventPublicInfoNote;

}
