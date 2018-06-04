package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, Long> {
}
