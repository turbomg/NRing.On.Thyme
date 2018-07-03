package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.LocationDefinition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDefinitionRepository extends CrudRepository<LocationDefinition, Long> {
}
