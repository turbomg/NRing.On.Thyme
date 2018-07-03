package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.Event;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

@SpringComponent
@UIScope
public class EventGridView {

    //todo finish this class

    private final EventRepository eventRepository;
    final Grid<Event> eventGrid;

    public EventGridView(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.eventGrid = new Grid<>(Event.class);
    }

    public VerticalLayout buildEventGridView() {

        VerticalLayout eventGridViewVL = new VerticalLayout();

        List<Event> eventList = (List<Event>) eventRepository.findAll();
        eventGrid.setItems(eventList);
        eventGridViewVL.addComponent(eventGrid);

        return eventGridViewVL;
    }
}
