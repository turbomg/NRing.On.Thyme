package com.katamlek.nringthymeleaf.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SpringView
@UIScope
public class CalendarView extends VerticalLayout implements View {

//    private EventRepository eventRepository;
//    private TemporaryPackageItemRepository temporaryPackageItemRepository;
//    TextField txt;
//
//    public CalendarView(EventRepository eventRepository, TemporaryPackageItemRepository temporaryPackageItemRepository) {
//        this.eventRepository = eventRepository;
//        this.temporaryPackageItemRepository = temporaryPackageItemRepository;
//
//        addComponent(buildCalendar());
//        addComponent(txt);
//        txt.setWidth("300px");
//        txt.setHeight("300px");
//        setSizeFull();
//    }
//
//    /**
//     * Builds the list of event counts by event and booking date.
//     * @return
//     * Map: event, date booked, count
//     */
//
//    // Create the full list of types, dates and counts
//    public Calendar<CalendarEntry> buildCalendar() {
//
//        // get the list of events
//        List<com.katamlek.nringthymeleaf.domain.Event> allEventsList = new ArrayList<>();
//        // in package item repository find all item related to package and add them to the allEventsList
//        allEventsList.addAll();
//
//        // group and count
////        Map<Date, List<com.katamlek.nringthymeleaf.domain.Event>> mapByDate = allEventsList.stream()
////                .collect(Collectors.groupingBy(event -> event.getEventStartDateTime()));
////
////        txt = new TextField();
////        txt.setValue(mapByDate.toString());
//
////        Map <String, Map <Date, String>> byGenderAndBirthDate = allEventsList.stream()
////                .collect(Collectors.groupingBy(p -> p.getEventName(),
////                        Collectors.groupingBy(p -> p.getEventStartDateTime(),
////                                Collectors.mapping(p -> p.getEventName(), Collectors.joining(", ")))));
//
//
////        Map<Date, Map<String, Long>> map =
////                allEventsList.stream()
////                        .collect(Collectors.groupingBy(e -> Date.from(e.getEventStartDateTime()), TreeMap::new,
////                                Collectors.groupingBy(x -> x.getName(), Collectors.counting()))
////                        );
//
//        Map<String, Integer> eventsDaily =
//
//
//     //   CalendarEntry calendarEntry = new CalendarEntry("caption", "description", ZonedDateTime.now(), ZonedDateTime.now(), new Date(), EventType.EVENT);
//
////       List<com.katamlek.nringthymeleaf.domain.Event> calendarEntryList = eventRepository.countEventByDate();
////
////        BasicItemProvider<CalendarEntry> entryProvider = new BasicItemProvider<>();
////        entryProvider.addItem(calendarEntry);
////
////        Calendar calendar = new Calendar(entryProvider);
//
//        return calendar;
//    }
//
//
// //   public CalendarItemProvider<>
//
//
//    // Pass the map<> to the calendar and create the data provided
//    // Place all the entries in the calendar
//}
}
