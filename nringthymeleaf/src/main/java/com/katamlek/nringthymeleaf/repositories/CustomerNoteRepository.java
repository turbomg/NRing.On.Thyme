package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.CustomerNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerNoteRepository extends CrudRepository<CustomerNote, Long> {
}
