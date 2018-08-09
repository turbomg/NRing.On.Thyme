package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.domain.CustomerNote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerNoteRepository extends CrudRepository<CustomerNote, Long> {

    public List<CustomerNote> findDistinctByCustomerAndHistoryNote(Customer customer, boolean isHistoryNote);
}
