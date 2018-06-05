package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.CustomerDocument;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDocumentRepository extends CrudRepository<CustomerDocument, Long> {
}
