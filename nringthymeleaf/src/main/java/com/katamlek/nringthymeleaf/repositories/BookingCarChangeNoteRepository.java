package com.katamlek.nringthymeleaf.repositories;

//todo check how it works with the superclass

import com.katamlek.nringthymeleaf.domain.BookingCarChangeNote;
import org.springframework.data.repository.CrudRepository;

public interface BookingCarChangeNoteRepository extends CrudRepository<BookingCarChangeNote, Long> {
}
