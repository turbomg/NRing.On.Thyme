package com.katamlek.nringthymeleaf.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String eventType;

    private String eventName;

    private String eventLocation;

    private String eventTrack;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar eventStartDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar eventEndDateTime;

    private String eventResponsibleUser;

    private boolean visibleWhenNoBookings;
    private boolean visibleInPublicCalendar;
}
