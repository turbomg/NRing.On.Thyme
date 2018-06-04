package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingNote;
import org.springframework.data.repository.CrudRepository;

public interface BookingNoteRepository extends CrudRepository<BookingNote, Long> {
}
