package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import com.katamlek.nringthymeleaf.domain.EventType;
import com.katamlek.nringthymeleaf.frontend.calendar.CalendarEntry;
import com.vaadin.data.converter.StringToIntegerConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public List<Event> findByEventStartDateTime(Date date);
    public Integer countEventByEventStartDateTimeAndEventName(String name, Date date);

//    @Query(value = "SELECT * FROM event GROUP BY event_date", nativeQuery = true)
//    public List<Event> countEventByDate();

}

// , COUNT(*) as count
