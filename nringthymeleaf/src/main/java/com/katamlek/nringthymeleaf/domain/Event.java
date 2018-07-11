package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @Temporal(TemporalType.DATE)
    private Date eventDate;

    private String eventStartTime;

    private String eventEndTime;

    @ManyToOne
    private User eventResponsibleUser;

    private boolean visibleWhenNoBookings;
    private boolean visibleInPublicCalendar;

    @OneToOne
    private EventPublicPricing eventPublicPricing;

    private boolean isUnderEditing; //todo add this property to every editable class

}
