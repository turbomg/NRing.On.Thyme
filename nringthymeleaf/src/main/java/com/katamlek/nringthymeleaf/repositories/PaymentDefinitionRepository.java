package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.PaymentDefinition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDefinitionRepository extends CrudRepository<PaymentDefinition, Long> {
}
