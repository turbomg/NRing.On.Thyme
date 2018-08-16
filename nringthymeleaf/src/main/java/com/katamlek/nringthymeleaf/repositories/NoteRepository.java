package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.EventNote;
import com.katamlek.nringthymeleaf.domain.Note;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface NoteRepository extends NoteBaseRepository<Note> {

}
