package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
}
