package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Note regarding another entity: car note, day note etc.
 * A base type of note.
 */

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredOn;

    @Lob
    private String text;
    private NoteStatus noteStatus;

    private boolean historyNote;

    @ManyToOne
    private User user; // entered by

    private boolean isUnderEditing;

}
