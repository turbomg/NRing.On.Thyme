package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingDocument;
import org.springframework.data.repository.CrudRepository;

public interface BookingDocumentRepository extends CrudRepository<BookingDocument, Long> {
}
