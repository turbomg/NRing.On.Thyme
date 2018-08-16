package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

    public List<Event> findByEventDate(Date date);
    public Integer countEventByEventDateAndEventName(String name, Date date);
}
