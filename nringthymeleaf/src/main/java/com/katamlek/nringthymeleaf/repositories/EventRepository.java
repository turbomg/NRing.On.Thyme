package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public List<Event> findByEventStartDateTime(Date date);
    public Integer countEventByEventStartDateTimeAndEventName(String name, Date date);

//    @Query(value = "SELECT * FROM event GROUP BY event_date", nativeQuery = true)
//    public List<Event> countEventByDate();

}

// , COUNT(*) as count
