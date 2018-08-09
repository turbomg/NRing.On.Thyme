package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.BookingNote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingNoteRepository extends CrudRepository<BookingNote, Long> {
   // @Query(value = "SELECT * FROM ")
    public List<BookingNote> findByBooking(Booking booking);
}
