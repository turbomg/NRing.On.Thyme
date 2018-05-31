package com.katamlek.nringthymeleaf.services;

import com.katamlek.nringthymeleaf.domain.Event;

public interface EventService {
    Iterable<Event> listAllEvents();

    Event getEventById(Long id);

    Event saveEvent(Event event);
}
