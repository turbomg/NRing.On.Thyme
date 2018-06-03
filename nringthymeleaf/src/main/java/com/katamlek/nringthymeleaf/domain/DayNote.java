package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
public class DayNote {

    private String id;
    private User enteredBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar enteredOn;

    @Temporal(TemporalType.DATE)
    private Calendar concernsDay;

    private String text;
    private NoteStatus noteStatus;

}
