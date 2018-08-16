package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.domain.CustomerNote;
import com.katamlek.nringthymeleaf.domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CustomerNoteRepository extends NoteBaseRepository<CustomerNote> {
    public List<CustomerNote> findCustomerNoteByCustomerAndHistoryNote(Customer customer, boolean isHistoryNote);

//    public Optional<List<CustomerNote>> findCustomerNoteByCustomerAndHistoryNote(Customer customer, boolean isHistoryNote);
}
