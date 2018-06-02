package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * Entry regarding another entity: car note, day note etc.
 * Liked OneToMany from that entity (except from day note).
 */

public class Entry {

    private String id;
    private EntryType entryType;

    @Temporal(TemporalType.DATE)
    private String text;

    private Calendar enteredOn;
    @Temporal(TemporalType.DATE)
    // TODO Mark as optional - check how
    // TODO Then they'll be listed as day notes if concerns date = today's date
    private Calendar concernsDay;
    private String description;
    private Long value;
}
