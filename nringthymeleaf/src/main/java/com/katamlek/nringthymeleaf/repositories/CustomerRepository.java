package com.katamlek.nringthymeleaf.repositories;

import com.katamlek.nringthymeleaf.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
