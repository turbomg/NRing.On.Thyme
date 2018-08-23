package com.katamlek.nringthymeleaf.frontend.calendar;

import com.katamlek.nringthymeleaf.domain.EventType;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.vaadin.addon.calendar.item.BasicItem;
import org.vaadin.addon.calendar.item.CalendarItem;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEntry extends BasicItem {
    private Date eventDate;
    private EventType eventType; // this is the event type just in case you didn't notice :)))
//    private Integer calendarEntriesNumber; // how many people booked the calendar
 //   private BigDecimal calendarEntryFee; // this is the event price for the driver


    public CalendarEntry(String caption, String description, ZonedDateTime startDate, ZonedDateTime endDate, Date eventDate, EventType eventType) {
        super(caption, description, startDate, endDate);
        this.eventDate = eventDate;
        this.eventType = eventType;
    }
}
