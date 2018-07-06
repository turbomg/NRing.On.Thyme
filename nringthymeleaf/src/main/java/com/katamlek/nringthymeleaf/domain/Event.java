package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private EventType eventType;

    @NotNull
    private String eventName;

    @OneToOne
    private LocationDefinition eventLocation;

    private String eventTrack;

    @Temporal(TemporalType.TIMESTAMP)
    private Date eventStartDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date eventEndDateTime;

    @ManyToOne
    private User eventResponsibleUser;

    private boolean visibleWhenNoBookings;
    private boolean visibleInPublicCalendar;

    @OneToOne
    private EventPublicPricing eventPublicPricing;
}
