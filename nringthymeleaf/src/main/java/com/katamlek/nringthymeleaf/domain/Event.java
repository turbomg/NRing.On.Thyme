package com.katamlek.nringthymeleaf.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private EventType eventType;

    @NotNull
    private String eventName;

    @OneToOne(cascade = CascadeType.ALL)
    private LocationDefinition eventLocation;

    private String eventTrack;

    //  @Temporal(TemporalType.DATE)
    private Date eventStartDateTime; // using datetime field later on
    private Date eventEndDateTime; // using datetime field later on

//    private String eventStartTime;
//
//    private String eventEndTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private User eventResponsibleUser;

    private boolean visibleWhenNoBookings;
    private boolean visibleInPublicCalendar;

    // Internal info
    private String eventOrganizer;

    // Current entries and value are calculated on the fly

    private BigDecimal eventCostToRSR;
    private Integer spacesBooked;

    // Public info
    private String eventCarTypes;
    private String eventTyreTypes;
    private String eventNoiseLimit;
    private String eventTrackdayFormat;
    private String eventPitlaneInUse;


    private BigDecimal eventEntryFee;
    private BigDecimal eventAdditionalDriverFee;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventNote> eventNoteList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private BookingPackageItemEvent bookingPackageItemEvent;

    @OneToOne
    private TemporaryPackageItem temporaryPackageItem;

    private boolean isUnderEditing; //todo add this property to every editable class

    // Overwrite some getters of Lombok to avoid NPE

    public User getEventResponsibleUser() {
        if (eventResponsibleUser == null) {
            eventResponsibleUser = new User();
        }
        return eventResponsibleUser;
    }

    public void setEventResponsibleUser(User eventResponsibleUser) {
        this.eventResponsibleUser = eventResponsibleUser;
    }

    public List<EventNote> getEventNoteList() {
        if (eventNoteList == null) {
            eventNoteList = new ArrayList<>();
        }
        return eventNoteList;
    }

    public void setEventNoteList(List<EventNote> eventNoteList) {
        this.eventNoteList = eventNoteList;
    }


    public void addEventNote(EventNote eventNote) {
        if (eventNote != null) {
            eventNote.setEvent(this);
            this.eventNoteList.add(eventNote);
        }
    }

    public void removeEventNote(EventNote eventNote) {
        if (eventNoteList != null && this.eventNoteList.contains(eventNote)) {
            this.eventNoteList.remove(eventNote);
            eventNote.setEvent(null);
        }
    }

    public BookingPackageItemEvent getBookingPackageItemEvent() {
        if (bookingPackageItemEvent == null) {
            bookingPackageItemEvent = new BookingPackageItemEvent();
        }
        return bookingPackageItemEvent;
    }

    public void setBookingPackageItemEvent(BookingPackageItemEvent bookingPackageItemEvent) {
        this.bookingPackageItemEvent = bookingPackageItemEvent;
    }

    public LocationDefinition getEventLocation() {
        if (eventLocation == null) {
            eventLocation = new LocationDefinition();
        }
        return eventLocation;
    }

    public void setEventLocation(LocationDefinition eventLocation) {
        this.eventLocation = eventLocation;
    }

    @Override
    public String toString() {
        return "Event{" + "eventType=" + eventType + ", eventName='" + eventName + '\'' + ", eventTrack='" + eventTrack + '\'' + ", eventOrganizer='" + eventOrganizer + '\'' + ", eventCostToRSR=" + eventCostToRSR + '}';
    }
}
