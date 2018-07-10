package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
