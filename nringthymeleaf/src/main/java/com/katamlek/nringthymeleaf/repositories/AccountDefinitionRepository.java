package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.AccountDefinition;
import org.springframework.data.repository.CrudRepository;

public interface AccountDefinitionRepository extends CrudRepository<AccountDefinition, Long> {
}
