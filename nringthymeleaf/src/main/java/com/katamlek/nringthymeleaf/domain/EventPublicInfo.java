package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class EventPublicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventCarTypes;
    private String eventTyreTypes;
    private String eventNoiseLimit;
    private String eventTrackdayFormat;
    private String eventPitlaneInUse;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventPublicInfo")
    private List<EventPublicInfoNote> eventPublicInfoNote;

}
