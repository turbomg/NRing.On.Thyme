package com.katamlek.nringthymeleaf.domain;

import com.katamlek.nringthymeleaf.domain.EventType;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;
import org.vaadin.addon.calendar.item.BasicItem;
import org.vaadin.addon.calendar.item.CalendarItem;

import javax.persistence.Lob;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEntry extends BasicItem {
    private Date eventDate;
    private EventType eventName;
    private Long numberOfEntries;
//    private Integer calendarEntriesNumber; // how many people booked the calendar
 //   private BigDecimal calendarEntryFee; // this is the event price for the driver

}
