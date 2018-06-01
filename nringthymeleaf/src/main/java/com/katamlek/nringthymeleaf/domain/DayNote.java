package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Daily notes class. Unrelated to any other class.
 * Entity data pulled to the day overview screen.
 */

@Entity
@Data
@NoArgsConstructor
public class DayNote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Temporal(TemporalType.DATE)
    Calendar date;

    String contents;
    EntryStatus entryStatus;
}
