package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.domain.CarNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarNoteRepository extends CrudRepository<CarNote, Long> {

    public List<CarNote> findDistinctByCarAndHistoryNote(Car car, boolean isHistory);
}
