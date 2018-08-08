package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.BookingPackageItemNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPackageItemNoteRepository extends CrudRepository<BookingPackageItemNote, Long> {
}
