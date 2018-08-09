package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.domain.CustomerDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerDocumentRepository extends CrudRepository<CustomerDocument, Long> {

    public List<CustomerDocument> findDistinctByCustomer(Customer customer);
}
