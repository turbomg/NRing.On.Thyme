package com.katamlek.nringthymeleaf.services_if_necessary;

import com.katamlek.nringthymeleaf.domain.Customer;

import java.util.Set;

public interface CustomerService {
    Set<Customer> getCustomers();
}
