package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.DayNote;
import org.springframework.data.repository.CrudRepository;

public interface DayNoteRepository extends CrudRepository<DayNote, Long> {
}
