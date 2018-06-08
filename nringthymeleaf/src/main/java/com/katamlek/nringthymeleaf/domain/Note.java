package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Note regarding another entity: car note, day note etc.
 */


// TODO https://vladmihalcea.com/how-to-inherit-properties-from-a-base-class-entity-using-mappedsuperclass-with-jpa-and-hibernate/
// TODO Which class relates to other classes? Check!

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar enteredOn;

    @Lob
    private String text;
    private NoteStatus noteStatus;

    private boolean isHistoryNote;

    @ManyToOne
    private User user; // entered by

}
