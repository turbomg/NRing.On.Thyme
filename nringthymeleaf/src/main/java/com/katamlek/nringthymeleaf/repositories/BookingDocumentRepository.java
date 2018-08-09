package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.BookingDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDocumentRepository extends CrudRepository<BookingDocument, Long> {

    public List<BookingDocument> findDistinctByBooking(Booking booking);
}
