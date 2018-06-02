package com.katamlek.nringthymeleaf.domain;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

/**
 * Entry regarding another entity: car note, day note etc.
 */

public class Entry {

    private String id;
    private EntryType entryType;
    private String text;
    @Temporal(TemporalType.DATE)
    // TODO Mark as optional - check how
    // TODO Maybe change history entries to entries
    // TODO Then they'll be listed as day notes if entry date = today's date
    private Calendar concernsDay;
}
