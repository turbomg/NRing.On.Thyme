package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
