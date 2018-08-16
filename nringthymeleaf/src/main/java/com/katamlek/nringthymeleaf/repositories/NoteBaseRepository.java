package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NoteBaseRepository<T extends Note> extends CrudRepository<T, Long> {

}
