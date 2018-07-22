package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.CarNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarNoteRepository extends CrudRepository<CarNote, Long> {

    //todo check if iterable ok - no NPE; maybe use Optional
    // find only non-history notes by car id
    Iterable<CarNote> findByCarIdAndAndHistoryNote(Long aLong, boolean isHistory);
}
