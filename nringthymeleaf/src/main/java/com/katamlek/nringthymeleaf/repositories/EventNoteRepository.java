package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Event;
import com.katamlek.nringthymeleaf.domain.EventNote;
import com.katamlek.nringthymeleaf.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventNoteRepository extends NoteBaseRepository<EventNote> {
    List<EventNote> findByEvent(Event event);
}
