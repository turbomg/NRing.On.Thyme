package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.CalendarEntry;
import com.katamlek.nringthymeleaf.domain.TemporaryPackageItem;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.TemporaryPackageItemRepository;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.assertj.core.util.Lists;
import org.vaadin.addon.calendar.Calendar;

import java.util.*;
import java.util.stream.Collectors;

@SpringView
@UIScope
public class CalendarView extends VerticalLayout implements View {

    //    private EventRepository eventRepository;
    private TemporaryPackageItemRepository temporaryPackageItemRepository;

    //    TextField txt;
//
    public CalendarView(EventRepository eventRepository, TemporaryPackageItemRepository temporaryPackageItemRepository) {
        this.temporaryPackageItemRepository = temporaryPackageItemRepository;

       // addComponent(buildCalendar());
        setSizeFull();
    }
//

    /**
     * Builds the map out of package items by groping by date, event and counting the items.
     *
     * @return Map: key date + event type, count
     */

    public Map<String, Integer> collectPackageItems() {
        // Get the list of package items to analyze
        List<TemporaryPackageItem> allItemsList = new ArrayList<>();
        allItemsList.addAll(Lists.newArrayList(temporaryPackageItemRepository.findAll()));

        Map<String, Integer> groupedItems = allItemsList.stream().collect(Collectors.groupingBy(TemporaryPackageItem::getCalendarKey, Collectors.summingInt(TemporaryPackageItem::getStatistiscCount)));

        return groupedItems;

    }

}

    // Create the full list of types, dates and counts
   // public Calendar<CalendarEntry> buildCalendar() {



        // group and count


//        Map<Date, List<TemporaryPackageItem> > groupedByDate = allItemsList.stream()
//                .collect(Collectors.groupingBy(TemporaryPackageItem::getStartDateTime));

        // group and count
//        Map<Date, List<com.katamlek.nringthymeleaf.domain.Event>> mapByDate = allEventsList.stream()
//                .collect(Collectors.groupingBy(event -> event.getEventStartDateTime()));
//
//        txt = new TextField();
//        txt.setValue(mapByDate.toString());

//        Map <String, Map <Date, String>> byGenderAndBirthDate = allEventsList.stream()
//                .collect(Collectors.groupingBy(p -> p.getEventName(),
//                        Collectors.groupingBy(p -> p.getEventStartDateTime(),
//                                Collectors.mapping(p -> p.getEventName(), Collectors.joining(", ")))));


//        Map<Date, Map<String, Long>> map =
//                allEventsList.stream()
//                        .collect(Collectors.groupingBy(e -> Date.from(e.getEventStartDateTime()), TreeMap::new,
//                                Collectors.groupingBy(x -> x.getName(), Collectors.counting()))
//                        );

      //  Map<String, Integer> eventsDaily =


     //   CalendarEntry calendarEntry = new CalendarEntry("caption", "description", ZonedDateTime.now(), ZonedDateTime.now(), new Date(), EventType.EVENT);

//       List<com.katamlek.nringthymeleaf.domain.Event> calendarEntryList = eventRepository.countEventByDate();
//
//        BasicItemProvider<CalendarEntry> entryProvider = new BasicItemProvider<>();
//        entryProvider.addItem(calendarEntry);
//
//        Calendar calendar = new Calendar(entryProvider);
//
//        return true;
//    }
//
//
// //   public CalendarItemProvider<>
//
//
//    // Pass the map<> to the calendar and create the data provided
//    // Place all the entries in the calendar
//}
//}
